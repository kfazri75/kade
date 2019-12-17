package dev.nuris.footballleague.module

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import dev.nuris.footballleague.R
import dev.nuris.footballleague.api.ApiRepository
import dev.nuris.footballleague.helper.*
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.Event
import dev.nuris.footballleague.model.EventResponse
import dev.nuris.footballleague.model.Favorite
import dev.nuris.footballleague.module.presenter.DetailMatchPresenter
import dev.nuris.footballleague.module.view.DetailMatchView
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.layout_details_event.view.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var event: Event
    private lateinit var eventId: String
    private var isFavorite: Boolean = false
    private lateinit var menuItem: MenuItem
    private lateinit var presenter: DetailMatchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventId = intent.getStringExtra(Constant.EVENT_ID)?:""
        presenter = DetailMatchPresenter(this, ApiRepository(), Gson())
        presenter.getDetailMatch(eventId)
    }

    override fun showLoading() = loadingRl.setVisible()

    override fun hideLoading() = loadingRl.setGone()

    override fun showDetailMatch(eventResponse: EventResponse) {
        if (!eventResponse.events.isNullOrEmpty()) {
            event = eventResponse.events[0]
            supportActionBar?.title = event.strLeague
            retrieveData(event)
            favoriteState()
        }
    }

    private fun retrieveData(event: Event) {
        eventNameTv.text = event.strEvent
        dateTv.text = (event.dateEvent + " " + event.strTime).convertGTMFormat()
        homeTeamTv.text = event.strHomeTeam
        awayTeamTv.text = event.strAwayTeam
        homeScoreTv.text = event.intHomeScore.checkNull()
        awayScoreTv.text = event.intAwayScore.checkNull()

        GlideApp.with(this)
            .load(Utility.linkJersey(event.idHomeTeam?:""))
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(homeTeamIv)

        GlideApp.with(this)
            .load(Utility.linkJersey(event.idAwayTeam?:""))
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(awayTeamIv)

        addViewDetails(
            title = getString(R.string.home),
            goalDetail = event.strHomeGoalDetails.checkNull(),
            redCard = event.strHomeRedCards.checkNull(),
            yellowCard = event.strHomeYellowCards.checkNull(),
            goalKeeper = event.strHomeLineupGoalkeeper.checkNull(),
            defense = event.strHomeLineupDefense.checkNull(),
            midfield = event.strHomeLineupMidfield.checkNull(),
            forward = event.strHomeLineupForward.checkNull(),
            substitutes = event.strHomeLineupSubstitutes.checkNull())
        addViewDetails(
            title = getString(R.string.away),
            goalDetail = event.strAwayGoalDetails.checkNull(),
            redCard = event.strAwayRedCards.checkNull(),
            yellowCard = event.strAwayYellowCards.checkNull(),
            goalKeeper = event.strAwayLineupGoalkeeper.checkNull(),
            defense = event.strAwayLineupDefense.checkNull(),
            midfield = event.strAwayLineupMidfield.checkNull(),
            forward = event.strAwayLineupForward.checkNull(),
            substitutes = event.strAwayLineupSubstitutes.checkNull())
    }

    @SuppressLint("InflateParams")
    private fun addViewDetails(title: String, goalDetail: String, redCard: String, yellowCard: String, goalKeeper: String,
                               defense: String, midfield: String, forward: String, substitutes: String){
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.layout_details_event, null)
        v.titleTv.text = title
        v.goalDetailTv.text = goalDetail
        v.redCardTv.text = redCard
        v.yellowCardTv.text = yellowCard
        v.goalKeeperTv.text = goalKeeper
        v.defenseTv.text = defense
        v.midfieldTv.text = midfield
        v.forwardTv.text = forward
        v.substitutesTv.text = substitutes
        detailLl.addView(v)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to event.idEvent,
                    Favorite.EVENT_NAME to event.strEvent,
                    Favorite.EVENT_DATE to (event.dateEvent + " " + event.strTime).convertGTMFormat(),
                    Favorite.HOME_ID to event.idHomeTeam,
                    Favorite.AWAY_ID to event.idAwayTeam,
                    Favorite.HOME_TEAM_NAME to event.strHomeTeam,
                    Favorite.AWAY_TEAM_NAME to event.strAwayTeam,
                    Favorite.HOME_SCORE to event.intHomeScore.checkNull(),
                    Favorite.AWAY_SCORE to event.intAwayScore.checkNull())
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
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to eventId)
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
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to eventId)
            val favorite = result.parseList(classParser<Favorite>())
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
