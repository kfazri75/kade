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
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.ui.adapter.MatchRvAdapter
import dev.nuris.footballleague.ui.viewmodel.SearchMatchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMatchFragment : Fragment() {

    private val viewModel by viewModel<SearchMatchViewModel>()
    private lateinit var events: Events
    private lateinit var searchEventRv: RecyclerView
    private lateinit var emptyImageCl: ConstraintLayout
    private lateinit var loadingRl: RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val v = inflater.inflate(R.layout.fragment_search_match, container, false)
        searchEventRv = v.findViewById(R.id.searchEventRv)
        emptyImageCl = v.findViewById(R.id.emptyImageCl)
        loadingRl = v.findViewById(R.id.loadingRl)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.searchMatchResponse.observe(this, Observer {
            loadingRl.setGone()
            if (Utility.checkApiResponse(requireActivity(), it)){
                events = it.apiData!!
                if (events.event.isNullOrEmpty()) {
                    searchEventRv.setGone()
                    emptyImageCl.setVisible()
                    return@Observer
                }
                if (events.event!!.filter { ev ->  ev.strSport == requireActivity().getString(R.string.soccer) }.isNullOrEmpty()) {
                    searchEventRv.setGone()
                    emptyImageCl.setVisible()
                    return@Observer
                }
                emptyImageCl.setGone()
                searchEventRv.setVisible()
                searchEventRv.adapter = assignAdapter(events.event!!.filter { ev -> ev.strSport == requireActivity().getString(R.string.soccer) })
            }
        })
    }

    private fun assignAdapter(list: List<Event>) : MatchRvAdapter {
        return MatchRvAdapter(requireActivity(), list) {
            val intent = Intent(requireActivity(), DetailMatchActivity::class.java)
            intent.putExtra(Constant.EVENT_ID, it.idEvent)
            startActivity(intent)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        requireActivity().menuInflater.inflate(R.menu.menu_search, menu)
        val itemSearch = menu.findItem(R.id.itemSearch)
        val searchView = itemSearch.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    loadingRl.setVisible()
                    viewModel.getSearchMatch(query)
                }
                return false
            }
        })
        super.onPrepareOptionsMenu(menu)
    }
}
