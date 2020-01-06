package dev.nuris.footballleague.module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.nuris.footballleague.R
import dev.nuris.footballleague.module.adapter.FavoriteVpAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.favorite)
            setDisplayHomeAsUpEnabled(true)
        }

        val fragmentList = listOf(FavoriteMatchFragment(), FavoriteTeamFragment())
        val titleList = listOf(getString(R.string.match), getString(R.string.teams))
        favoriteVp.adapter = FavoriteVpAdapter(supportFragmentManager, fragmentList, titleList)
        favoriteTl.setupWithViewPager(favoriteVp)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
