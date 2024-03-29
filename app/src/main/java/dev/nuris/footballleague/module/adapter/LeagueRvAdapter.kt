package dev.nuris.footballleague.module.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.League
import kotlinx.android.synthetic.main.layout_league_item.view.*

class LeagueRvAdapter (private val list: List<League>,
                       private val listener: (League) -> Unit
) : RecyclerView.Adapter<LeagueRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.context, LayoutInflater.from(parent.context).inflate(R.layout.layout_league_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(list[position], listener)

    class ViewHolder(private val context: Context, private val v: View) : RecyclerView.ViewHolder(v) {
        fun bindItem(league: League, listener: (League) -> Unit) {
            league.let {
                v.leagueTv.text = it.strLeague
                GlideApp.with(context)
                    .load("${league.strBadge}/preview")
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(v.leagueIv)
                itemView.setOnClickListener { _ ->
                    listener(it)
                }
            }
        }
    }
}