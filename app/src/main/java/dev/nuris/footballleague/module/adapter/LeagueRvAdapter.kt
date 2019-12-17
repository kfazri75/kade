package dev.nuris.footballleague.module.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.League

class LeagueRvAdapter (private val context: Context,
                       private val list: List<League>,
                       private val listener: (League) -> Unit
) : RecyclerView.Adapter<LeagueRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_league_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(context, list[position], listener)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.leagueTv)
        private val image = view.findViewById<ImageView>(R.id.leagueIv)

        fun bindItem(context: Context, league: League, listener: (League) -> Unit) {
            name.text = league.strLeague

            GlideApp.with(context)
                .load("${league.strBadge}/preview")
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(image)

            itemView.setOnClickListener {
                listener(league)
            }
        }
    }
}