package dev.nuris.footballleague.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.color
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.League
import dev.nuris.footballleague.ui.adapter.MatchVpAdapter
import dev.nuris.footballleague.ui.viewmodel.LeagueDetailViewModel
import kotlinx.android.synthetic.main.activity_league_detail.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.abs

class LeagueDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<LeagueDetailViewModel>()
    private lateinit var league: League
    private lateinit var leagueId: String
    private var menu: Menu? = null

    companion object{
        const val LEAGUE_ID = "leagueId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)
        leagueId = intent.getStringExtra(LEAGUE_ID)!!
        setSupportActionBar(leagueTb)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.leagueDetailResponse.observe(this, Observer {
            loadingRl.setGone()
            if (Utility.checkApiResponse(this, it)) {
                league = it.apiData!!.leagueDetail!![0]
                retrieveData()
            }
        })
        loadingRl.setVisible()
        viewModel.getLeagueDetails(leagueId)
    }

    private fun retrieveData() {
        leagueTv.text = league.strLeague
        descTv.text = if (!league.strDescriptionEN.isNullOrBlank()) league.strDescriptionEN!!.take(182) + "..." else league.strDescriptionEN
        GlideApp.with(this)
            .load("${league.strBadge}/preview")
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(badgeIv)
        GlideApp.with(this)
            .load(league.strBanner)
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(headerIv)

        leagueAbl.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            leagueAbl.post {
                if (abs(verticalOffset) == 0) {
                    leagueDetailLl.setVisible()
                    leagueTb.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_white)
                    leagueTb.title = ""
                    if (menu != null) leagueTb.menu.getItem(0).setIcon(R.drawable.ic_search_white)
                    matchTl.tabTextColors = ColorStateList.valueOf(color(android.R.color.white))
                    matchTl.setSelectedTabIndicatorColor(color(android.R.color.white))
                } else {
                    leagueDetailLl.setGone()
                    leagueTb.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_gray)
                    leagueTb.setTitleTextColor(color(R.color.colorPrimaryDark))
                    leagueTb.title = league.strLeague
                    if (menu != null) leagueTb.menu.getItem(0).setIcon(R.drawable.ic_search)
                    matchTl.tabTextColors = ColorStateList.valueOf(color(R.color.colorPrimaryDark))
                    matchTl.setSelectedTabIndicatorColor(color(R.color.colorPrimaryDark))
                }
            }
        })

        val fragmentList = listOf(PastMatchFragment(), NextMatchFragment())
        val titleList = listOf("Past Match", "Next Match")
        matchVp.adapter = MatchVpAdapter(supportFragmentManager, fragmentList, titleList, leagueId)
        matchTl.setupWithViewPager(matchVp)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
