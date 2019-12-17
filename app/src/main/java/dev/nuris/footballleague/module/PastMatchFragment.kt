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
import dev.nuris.footballleague.module.presenter.PastMatchPresenter
import dev.nuris.footballleague.module.view.PastMatchView
import kotlinx.android.synthetic.main.fragment_match.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*

class PastMatchFragment : Fragment(), PastMatchView {

    private lateinit var presenter: PastMatchPresenter
    private lateinit var leagueId: String
    private lateinit var adapter: MatchRvAdapter
    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_match, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        leagueId = arguments?.getString(Constant.LEAGUE_ID)?:""
        presenter = PastMatchPresenter(this, ApiRepository(), Gson())
        presenter.getPastMatchList(leagueId)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showPastMatchList(eventResponse: EventResponse) {
        eventResponse.let {
            adapter = assignRvAdapter(it.events?: listOf())
            if (adapter.itemCount == 0) {
                v.emptyImageCl.setVisible()
                v.matchRv.setGone()
            } else {
                v.emptyImageCl.setGone()
                v.matchRv.setVisible()
                v.matchRv.adapter = adapter
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
