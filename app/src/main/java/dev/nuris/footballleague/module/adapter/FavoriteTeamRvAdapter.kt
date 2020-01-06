package dev.nuris.footballleague.module.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.nuris.footballleague.R
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.FavoriteTeam
import kotlinx.android.synthetic.main.layout_team_item.view.*

class FavoriteTeamRvAdapter (private val list: List<FavoriteTeam>,
                             private val listener: (FavoriteTeam) -> Unit
) : RecyclerView.Adapter<FavoriteTeamRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.context, LayoutInflater.from(parent.context).inflate(R.layout.layout_team_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(list[position], listener)

    class ViewHolder(private val context: Context, private val v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("SetTextI18n")
        fun bindItem(team: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            team.let {
                v.teamNameTv.text = it.name
                v.descTv.text = if (it.desc?.length?:0 < 180) it.desc else it.desc?.take(180) + " ..."
                GlideApp.with(context)
                    .load(it.badge)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(v.teamIv)
                itemView.setOnClickListener { _ ->
                    listener(it)
                }
            }
        }
    }
}