package dev.nuris.footballleague.module

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dev.nuris.footballleague.module.presenter.MatchPresenter
import dev.nuris.footballleague.module.view.MatchView
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_match.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*

class MatchFragment : Fragment(), MatchView {

    private lateinit var presenter: MatchPresenter
    private lateinit var leagueId: String
    private lateinit var v: View
    private var nextMatch = mutableListOf<Event>()
    private var lastMatch = mutableListOf<Event>()
    private lateinit var nextAdapter: MatchRvAdapter
    private lateinit var lastAdapter: MatchRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_match, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leagueId = arguments?.getString(Constant.LEAGUE_ID)?:""
        presenter = MatchPresenter(this, ApiRepository(), Gson())
        presenter.getNextMatchList(leagueId)
        presenter.getLastMatchList(leagueId)
        nextAdapter = assignRvAdapter(nextMatch)
        lastAdapter = assignRvAdapter(lastMatch)
        v.nextRv.adapter = nextAdapter
        v.lastRv.adapter = lastAdapter
    }

    override fun showNextMatchList(eventResponse: EventResponse) {
        eventResponse.let {
            nextMatch.clear()
            nextMatch.addAll(it.events?: listOf())
            nextAdapter.notifyDataSetChanged()
            if (nextAdapter.itemCount == 0) {
                nextRv.setGone()
                nextNotFound.setVisible()
            } else {
                nextRv.setVisible()
                nextNotFound.setGone()
            }
        }
    }

    override fun showLastMatchList(eventResponse: EventResponse) {
        eventResponse.let {
            lastMatch.clear()
            lastMatch.addAll(it.events?: listOf())
            lastAdapter.notifyDataSetChanged()
            if (lastAdapter.itemCount == 0) {
                lastRv.setGone()
                lastNotFound.setVisible()
            } else {
                lastRv.setVisible()
                lastNotFound.setGone()
            }
        }
    }

    override fun showLoading() = v.loadingRl.setVisible()

    override fun hideLoading() = v.loadingRl.setGone()

    private fun assignRvAdapter(list: List<Event>) : MatchRvAdapter {
        return MatchRvAdapter(requireActivity(), list) {
            val intent = Intent(requireActivity(), DetailMatchActivity::class.java)
            intent.putExtra(Constant.EVENT_ID, it.idEvent)
            startActivity(intent)
        }
    }
}
