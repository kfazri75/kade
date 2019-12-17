package dev.nuris.footballleague.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.nuris.footballleague.R
import dev.nuris.footballleague.module.adapter.MainVpAdapter
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        val fragmentList = listOf(LeagueFragment(), SearchMatchFragment(), FavoriteFragment())
        val vpAdapter = MainVpAdapter(supportFragmentManager, fragmentList)
        mainVp.apply {
            adapter = vpAdapter
            offscreenPageLimit = fragmentList.size
            setPagingEnabled(false)
        }

        mainBnv.setOnNavigationItemSelectedListener {
            mainVp.setCurrentItem(mainBnv.menu.findItem(it.itemId).order, false)
            supportActionBar?.title = getString(
                when(mainVp.currentItem) {
                    0 -> R.string.app_name
                    1 -> R.string.search
                    2 -> R.string.favorite
                    else -> R.string.app_name
                }
            )
            true
        }
    }
}
