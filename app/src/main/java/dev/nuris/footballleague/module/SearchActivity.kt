package dev.nuris.footballleague.module

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.Constant
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.adapter.MatchRvAdapter
import dev.nuris.footballleague.module.adapter.TeamRvAdapter
import dev.nuris.footballleague.module.presenter.SearchPresenter
import dev.nuris.footballleague.module.view.SearchView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SearchActivity : AppCompatActivity(), SearchView {

    private lateinit var presenter: SearchPresenter
    private lateinit var matchAdapter: MatchRvAdapter
    private lateinit var teamAdapter: TeamRvAdapter
    private var teams = mutableListOf<Team>()
    private var match = mutableListOf<Event>()

    private var isTeam = false

    companion object{
        const val IS_TEAM = "isTeam"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        isTeam = intent.getBooleanExtra(IS_TEAM, false)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(if (isTeam) R.string.search_teams else R.string.search_match)
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = SearchPresenter(this, ApiRepository(), Gson())

        matchAdapter = MatchRvAdapter(this, match) {
            startActivity(Intent(this, DetailMatchActivity::class.java).putExtra(Constant.EVENT_ID, it.idEvent))
        }

        teamAdapter = TeamRvAdapter(teams) {
            startActivity(Intent(this, TeamDetailActivity::class.java).putExtra(TeamDetailActivity.TEAM_ID, it.idTeam))
        }

        searchRv.adapter = if (isTeam) teamAdapter else matchAdapter
    }

    override fun hideLoading() = loadingRl.setGone()
    override fun showLoading() = loadingRl.setVisible()
    override fun showMatchList(eventResponse: EventResponse) {
        eventResponse.let {
            match.clear()
            match.addAll(it.event?: listOf())
            matchAdapter.notifyDataSetChanged()
            if (matchAdapter.itemCount == 0) {
                searchRv.setGone()
                emptyImageCl.setVisible()
            } else {
                searchRv.setVisible()
                emptyImageCl.setGone()
            }
        }
    }

    override fun showTeamList(teamResponse: TeamResponse) {
        teamResponse.let {
            teams.clear()
            teams.addAll(it.teams?: listOf())
            teamAdapter.notifyDataSetChanged()
            if (teamAdapter.itemCount == 0) {
                searchRv.setGone()
                emptyImageCl.setVisible()
            } else {
                searchRv.setVisible()
                emptyImageCl.setGone()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val itemSearch = menu?.findItem(R.id.itemSearch) as MenuItem
        val sv = itemSearch.actionView as androidx.appcompat.widget.SearchView
        itemSearch.expandActionView()
        sv.queryHint = getString(R.string.search)
        sv.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.let {
                    if (!it.isNullOrBlank()) {
                        presenter.apply {
                            if (isTeam) getTeamList(query?:"") else getMatchList(query?:"")
                        }
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
