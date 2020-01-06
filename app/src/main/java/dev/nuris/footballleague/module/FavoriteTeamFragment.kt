package dev.nuris.footballleague.module

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration

import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.database
import dev.nuris.footballleague.helper.setGone
import dev.nuris.footballleague.helper.setVisible
import dev.nuris.footballleague.model.FavoriteTeam
import dev.nuris.footballleague.module.adapter.FavoriteTeamRvAdapter
import kotlinx.android.synthetic.main.fragment_favorite_team.view.*
import kotlinx.android.synthetic.main.layout_empty_list.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamFragment : Fragment() {

    private lateinit var v: View
    private lateinit var adapter: FavoriteTeamRvAdapter
    private var favorites = mutableListOf<FavoriteTeam>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.teamRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        adapter = FavoriteTeamRvAdapter(favorites) {
            startActivity(Intent(requireActivity(), TeamDetailActivity::class.java).putExtra(TeamDetailActivity.TEAM_ID, it.teamId))
        }
        v.teamRv.adapter = adapter
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            if (adapter.itemCount <= 0) {
                v.emptyImageCl.setVisible()
                v.teamRv.setGone()
            } else {
                v.emptyImageCl.setGone()
                v.teamRv.setVisible()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
}
