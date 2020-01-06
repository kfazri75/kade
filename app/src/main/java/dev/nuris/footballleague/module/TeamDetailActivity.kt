package dev.nuris.footballleague.module

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.database
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.FavoriteTeam
import dev.nuris.footballleague.model.Team
import dev.nuris.footballleague.model.TeamResponse
import dev.nuris.footballleague.module.presenter.TeamDetailPresenter
import dev.nuris.footballleague.module.view.TeamDetailView
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private var isFavorite: Boolean = false
    private lateinit var menuItem: MenuItem
    private lateinit var team: Team
    private lateinit var teamId: String

    companion object{
        const val TEAM_ID = "teamID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(true)
        }
        teamId = intent.getStringExtra(TEAM_ID)?:""
        presenter = TeamDetailPresenter(this, ApiRepository(), Gson())
        presenter.getTeamList(teamId)
    }

    override fun showLoading() = loadingRl.setVisible()
    override fun hideLoading() = loadingRl.setGone()
    override fun showTeamDetail(teamResponse: TeamResponse) {
        if (!teamResponse.teams.isNullOrEmpty()) {
            team = teamResponse.teams[0]
            team.let {
                supportActionBar?.title = it.strTeam
                GlideApp.with(this)
                    .load(it.strTeamLogo)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(teamIv)
                teamNameTv.text = it.strTeam
                leagueNameTV.text = it.strLeague
                descTv.text = it.strDescriptionEN
            }
            favoriteState()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to team.idTeam,
                    FavoriteTeam.TEAM_NAME to team.strTeam,
                    FavoriteTeam.TEAM_DESC to team.strDescriptionEN,
                    FavoriteTeam.BADGE to team.strTeamBadge)
            }

            isFavorite = true
            setIconFavorite()
            Toast.makeText(this, getString(R.string.added_to_favorite), Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to teamId)
            }
            isFavorite = false
            setIconFavorite()
            Toast.makeText(this, getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (favorite.isNotEmpty()) isFavorite = true
            setIconFavorite()
        }
    }

    private fun setIconFavorite() {
        menuItem.icon = getDrawable(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
    }

    @SuppressLint("DefaultLocale")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu?.findItem(R.id.itemFavorite)!!
        menuItem.setOnMenuItemClickListener {
            if (isFavorite) removeFromFavorite() else addToFavorite()
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
