package dev.nuris.footballleague.module

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.database
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Favorite
import dev.nuris.footballleague.module.adapter.FavoriteRvAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment() {

    private lateinit var v: View
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_favorite, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter = assignRvAdapter(favorites)
        v.favoriteEventRv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            if (adapter.itemCount <= 0) {
                v.emptyImageCl.setVisible()
                v.favoriteEventRv.setGone()
            } else {
                v.emptyImageCl.setGone()
                v.favoriteEventRv.setVisible()
            }
        }
    }

    private fun assignRvAdapter(list: List<Favorite>) : FavoriteRvAdapter {
        return FavoriteRvAdapter(requireContext(), list) {
            val intent = Intent(context, DetailMatchActivity::class.java)
            intent.putExtra(Constant.EVENT_ID, it.eventId)
            startActivity(intent)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        requireActivity().menuInflater.inflate(R.menu.menu_search, menu)
        val itemSearch = menu.findItem(R.id.itemSearch)
        val searchView = itemSearch.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            @SuppressLint("DefaultLocale")
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList(favorites)
                favorites.forEach {
                    if (!it.eventName?.toLowerCase()?.contains(newText?.toLowerCase()?:"")!!){
                        filteredList.remove(it)
                    }
                }
                adapter = assignRvAdapter(filteredList)
                v.favoriteEventRv.adapter = adapter
                if (adapter.itemCount <= 0) {
                    v.emptyImageCl.setVisible()
                    v.favoriteEventRv.setGone()
                } else {
                    v.emptyImageCl.setGone()
                    v.favoriteEventRv.setVisible()
                }
                return false
            }
        })

        super.onPrepareOptionsMenu(menu)
    }
}
