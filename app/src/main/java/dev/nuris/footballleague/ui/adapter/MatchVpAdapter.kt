package dev.nuris.footballleague.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.nuris.footballleague.helper.Constant

class MatchVpAdapter(fm: FragmentManager,
                     private val fragmentList: List<Fragment>,
                     private val titleList: List<String>,
                     private val leagueId: String) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence?  = titleList[position]

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(Constant.LEAGUE_ID, leagueId)
        fragmentList[position].arguments = bundle
        return fragmentList[position]
    }
}