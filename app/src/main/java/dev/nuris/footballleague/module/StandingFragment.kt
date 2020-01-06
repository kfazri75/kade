package dev.nuris.footballleague.module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.StandingResponse
import dev.nuris.footballleague.model.Table
import dev.nuris.footballleague.module.adapter.StandingRvAdapter
import dev.nuris.footballleague.module.presenter.StandingPresenter
import dev.nuris.footballleague.module.view.StandingView
import kotlinx.android.synthetic.main.fragment_standing.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*

class StandingFragment : Fragment(), StandingView {

    private lateinit var v: View
    private lateinit var presenter: StandingPresenter
    private lateinit var adapter: StandingRvAdapter
    private var table = mutableListOf<Table>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_standing, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = StandingPresenter(this, ApiRepository(), Gson())
        presenter.getLeagueList(arguments?.getString(Constant.LEAGUE_ID)?:"")
        adapter = StandingRvAdapter(table)
        v.standingRv.adapter = adapter
    }

    override fun hideLoading() = v.loadingRl.setGone()
    override fun showLoading() = v.loadingRl.setVisible()
    override fun showStandingList(standingResponse: StandingResponse) {
        standingResponse.let {
            table.clear()
            table.addAll(it.table?: listOf())
            adapter.notifyDataSetChanged()
        }
    }

}
