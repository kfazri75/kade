package dev.nuris.footballleague.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.response.Events
import dev.nuris.footballleague.ui.adapter.MatchRvAdapter
import dev.nuris.footballleague.ui.viewmodel.LeagueDetailViewModel
import kotlinx.android.synthetic.main.fragment_match.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class NextMatchFragment : Fragment() {

    private val viewModel by viewModel<LeagueDetailViewModel>()

    private lateinit var events: Events
    private lateinit var leagueId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        leagueId = arguments?.getString(Constant.LEAGUE_ID)!!
        setupObserver(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObserver(v: View) {
        viewModel.nextEventResponse.observe(this, Observer {
            v.loadingRl.setGone()
            if (Utility.checkApiResponse(requireActivity(), it)) {
                events = it.apiData!!
                if (events.events.isNullOrEmpty()) {
                    v.emptyImageCl.setVisible()
                    v.matchRv.setGone()
                    return@Observer
                }
                v.emptyImageCl.setGone()
                v.matchRv.setVisible()
                v.matchRv.adapter = assignRvAdapter(events.events!!)
            }
        })
        v.loadingRl.setVisible()
        viewModel.getNextEvent(leagueId)
    }

    private fun assignRvAdapter(list: List<Event>) : MatchRvAdapter {
        return MatchRvAdapter(requireActivity(), list) {
            val intent = Intent(requireActivity(), DetailMatchActivity::class.java)
            intent.putExtra(Constant.EVENT_ID, it.idEvent)
            startActivity(intent)
        }
    }
}
