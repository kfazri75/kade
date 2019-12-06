package dev.nuris.footballleague.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.ui.adapter.LeagueRvAdapter
import dev.nuris.footballleague.ui.viewmodel.LeagueViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class LeagueFragment : Fragment() {

    private val viewModel by viewModel<LeagueViewModel>()
    private lateinit var leagueList: List<League>
    private lateinit var leagueSrl: SwipeRefreshLayout
    private lateinit var leagueListRv: RecyclerView
    private lateinit var emptyImageCl: ConstraintLayout
    private lateinit var loadingRl: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_league, container, false)
        leagueSrl = v.findViewById(R.id.leagueSrl)
        leagueListRv = v.findViewById(R.id.leagueListRv)
        emptyImageCl = v.findViewById(R.id.emptyImageCl)
        loadingRl = v.findViewById(R.id.loadingRl)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        leagueSrl.setOnRefreshListener { viewModel.getLeague() }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.leagueResponse.observe(this, Observer {
            loadingRl.setGone()
            leagueSrl.isRefreshing = false
            if (Utility.checkApiResponse(requireActivity(), it)) {
                leagueList = it.apiData!!.leagues!!
                val adapter = assignRvAdapter(leagueList.filter { league -> league.strSport == requireActivity().getString(R.string.soccer)  })
                leagueListRv.adapter = adapter
                if (adapter.itemCount <= 0) {
                    emptyImageCl.setVisible()
                } else {
                    emptyImageCl.setGone()
                }
            }
        })
        loadingRl.setVisible()
        viewModel.getLeague()
    }

    private fun assignRvAdapter(list: List<League>) : LeagueRvAdapter {
        return LeagueRvAdapter(requireContext(), list) {
            val intent = Intent(requireActivity(), LeagueDetailActivity::class.java)
            intent.putExtra(LeagueDetailActivity.LEAGUE_ID, it.idLeague)
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
                val filteredList = ArrayList(leagueList)
                if (newText == null || newText.trim().isEmpty()) {
                    leagueSrl.isEnabled = true
                } else {
                    leagueSrl.isEnabled = false
                    leagueList.forEach {
                        if (!it.strLeague?.toLowerCase(Locale.US)!!.contains(newText.toLowerCase(Locale.US))) {
                            filteredList.remove(it)
                        }
                    }
                }
                val adapter = assignRvAdapter(filteredList)
                leagueListRv.adapter = adapter
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
