package dev.nuris.footballleague.module

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), LeagueView {

    private lateinit var presenter: LeaguePresenter
    private lateinit var adapter: LeagueRvAdapter
    private var leagues = mutableListOf<League>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        presenter = LeaguePresenter(this, ApiRepository(), Gson())
        presenter.getLeagueList()
        adapter = LeagueRvAdapter(leagues) {
            startActivity(Intent(this, LeagueDetailActivity::class.java)
                .putExtra(LeagueDetailActivity.LEAGUE_ID, it.idLeague))
        }
        mainRv.adapter = adapter
    }

    override fun hideLoading() = loadingRl.setGone()
    override fun showLoading() = loadingRl.setVisible()
    override fun showListLeague(leagueResponse: LeagueResponse) {
        leagueResponse.let {
            leagues.clear()
            leagues.addAll(it.leagues?: listOf())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.itemFavorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
