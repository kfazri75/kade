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
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.module.adapter.MatchRvAdapter
import dev.nuris.footballleague.module.presenter.SearchMatchPresenter
import dev.nuris.footballleague.module.view.SearchMatchView
import kotlinx.android.synthetic.main.fragment_search_match.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*

class SearchMatchFragment : Fragment(), SearchMatchView {

    private lateinit var presenter: SearchMatchPresenter
    private lateinit var v: View
    private lateinit var adapter: MatchRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        v = inflater.inflate(R.layout.fragment_search_match, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        presenter = SearchMatchPresenter(this, ApiRepository(), Gson())
    }

    override fun showLoading() = v.loadingRl.setVisible()

    override fun hideLoading() = v.loadingRl.setGone()

    override fun showMatchList(eventResponse: EventResponse) {
        val response = eventResponse.event?.filter { event -> event.strSport == requireActivity().getString(R.string.soccer)  }
        adapter = assignAdapter(response?: listOf())
        if (adapter.itemCount == 0) {
            v.searchEventRv.setGone()
            v.emptyImageCl.setVisible()
        } else {
            v.emptyImageCl.setGone()
            v.searchEventRv.setVisible()
            v.searchEventRv.adapter = adapter
        }
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
                query.let {
                    if (!it.isNullOrBlank()) {
                        presenter.getMatchList(it)
                    }
                }
                return false
            }
        })
        super.onPrepareOptionsMenu(menu)
    }
}
