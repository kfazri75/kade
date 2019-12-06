package dev.nuris.footballleague.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Favorite
import dev.nuris.footballleague.ui.adapter.FavoriteRvAdapter
import dev.nuris.footballleague.ui.viewmodel.FavoriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment() {

    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var favoriteRv: RecyclerView
    private lateinit var emptyImageCl: ConstraintLayout
    private lateinit var favorites: List<Favorite>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteRv = v.findViewById(R.id.favoriteEventRv)
        emptyImageCl = v.findViewById(R.id.emptyImageCl)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getFavorite()
    }

    private fun getFavorite() {
        viewModel.favoriteData.observe(this, Observer {
            favorites = it
            val adapter = assignRvAdapter(favorites)
            favoriteRv.adapter = adapter
            if (adapter.itemCount <= 0) {
                emptyImageCl.setVisible()
            } else {
                emptyImageCl.setGone()
            }
        })
        viewModel.getFavorite(requireActivity())
    }

    private fun assignRvAdapter(list: List<Favorite>) : FavoriteRvAdapter {
        return FavoriteRvAdapter(context!!, list) {
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
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList(favorites)
                favorites.forEach {
                    if (!it.eventName?.toLowerCase(Locale.US)!!.contains(newText!!.toLowerCase(Locale.US))) {
                        filteredList.remove(it)
                    }
                }
                val adapter = assignRvAdapter(filteredList)
                favoriteRv.adapter = adapter
                if (adapter.itemCount <= 0) {
                    emptyImageCl.setVisible()
                } else {
                    emptyImageCl.setGone()
                }
                return false
            }
        })

        super.onPrepareOptionsMenu(menu)
    }
}
