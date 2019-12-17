package dev.nuris.footballleague.module

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.model.LeagueResponse
import dev.nuris.footballleague.module.adapter.LeagueRvAdapter
import dev.nuris.footballleague.module.presenter.LeaguePresenter
import dev.nuris.footballleague.module.view.LeagueView
import kotlinx.android.synthetic.main.fragment_league.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*
import java.util.*
import kotlin.collections.ArrayList

class LeagueFragment : Fragment(), LeagueView {

    private lateinit var presenter: LeaguePresenter
    private var leagueList: MutableList<League> = mutableListOf()
    private lateinit var v: View
    private lateinit var adapter: LeagueRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_league, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = LeaguePresenter(this, ApiRepository(), Gson())
        presenter.getLeagueList()
        setHasOptionsMenu(true)

        adapter = assignRvAdapter(leagueList)
        v.leagueListRv.adapter = adapter

        v.leagueSrl.setOnRefreshListener {
            v.leagueSrl.isRefreshing = false
            presenter.getLeagueList()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun assignRvAdapter(list: List<League>) : LeagueRvAdapter {
        return LeagueRvAdapter(requireContext(), list) {
            val intent = Intent(requireActivity(), LeagueDetailActivity::class.java)
            intent.putExtra(LeagueDetailActivity.LEAGUE_ID, it.idLeague)
            startActivity(intent)
        }
    }

    override fun showListLeague(leagueResponse: LeagueResponse) {
        v.leagueSrl.isRefreshing = false
        leagueList.clear()
        leagueList.addAll(leagueResponse.leagues?: listOf())
        adapter.notifyDataSetChanged()

        if (adapter.itemCount <= 0) {
            v.emptyImageCl.setVisible()
            v.leagueListRv.setGone()
        } else {
            v.leagueListRv.setVisible()
            v.emptyImageCl.setGone()
        }
    }

    override fun showLoading() = v.loadingRl.setVisible()

    override fun hideLoading() = v.loadingRl.setGone()

    override fun onPrepareOptionsMenu(menu: Menu) {
        requireActivity().menuInflater.inflate(R.menu.menu_search, menu)
        val itemSearch = menu.findItem(R.id.itemSearch)
        val searchView = itemSearch.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList(leagueList)
                if (newText == null || newText.trim().isEmpty()) {
                    v.leagueSrl.isEnabled = true
                } else {
                    v.leagueSrl.isEnabled = false
                    leagueList.forEach {
                        if (!it.strLeague?.toLowerCase(Locale.US)!!.contains(newText.toLowerCase(Locale.US))) {
                            filteredList.remove(it)
                        }
                    }
                }
                val adapter = assignRvAdapter(filteredList)
                v.leagueListRv.adapter = adapter
                if (adapter.itemCount <= 0) {
                    v.emptyImageCl.setVisible()
                } else {
                    v.emptyImageCl.setGone()
                }
                return false
            }
        })

        super.onPrepareOptionsMenu(menu)
    }
}
