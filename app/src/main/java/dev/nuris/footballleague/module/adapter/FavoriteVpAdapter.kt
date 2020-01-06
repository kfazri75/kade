package dev.nuris.footballleague.module.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavoriteVpAdapter(fm: FragmentManager,
                        private val fragmentList: List<Fragment>,
                        private val titleList: List<String>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence?  = titleList[position]

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}