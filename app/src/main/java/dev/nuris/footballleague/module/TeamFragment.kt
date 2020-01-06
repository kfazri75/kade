package dev.nuris.footballleague.module


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.Gson
import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.adapter.TeamRvAdapter
import dev.nuris.footballleague.module.presenter.TeamPresenter
import dev.nuris.footballleague.module.view.TeamView
import kotlinx.android.synthetic.main.fragment_team.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import kotlinx.android.synthetic.main.layout_loading.view.*

class TeamFragment : Fragment(), TeamView {

    private lateinit var v: View
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamRvAdapter
    private var teams = mutableListOf<Team>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_team, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TeamPresenter(this, ApiRepository(), Gson())
        presenter.getTeamList(arguments?.getString(Constant.LEAGUE_ID)?:"")
        v.teamRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        adapter = TeamRvAdapter(teams) {
            startActivity(Intent(requireActivity(), TeamDetailActivity::class.java).putExtra(TeamDetailActivity.TEAM_ID, it.idTeam))
        }
        v.teamRv.adapter = adapter
    }

    override fun hideLoading() = v.loadingRl.setGone()
    override fun showLoading() = v.loadingRl.setVisible()
    override fun showTeamList(teamResponse: TeamResponse) {
        teamResponse.let {
            teams.clear()
            teams.addAll(it.teams?: listOf())
            adapter.notifyDataSetChanged()
            if (adapter.itemCount == 0) {
                v.teamRv.setGone()
                v.emptyImageCl.setVisible()
            } else {
                v.teamRv.setVisible()
                v.emptyImageCl.setGone()
            }
        }
    }

}
