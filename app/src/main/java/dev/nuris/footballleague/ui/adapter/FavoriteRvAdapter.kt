package dev.nuris.footballleague.ui.adapter

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
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.Favorite

class FavoriteRvAdapter (private val context: Context,
                         private val list: List<Favorite>,
                         private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_match_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(context, list[position], listener)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.findViewById<TextView>(R.id.eventNameTv)
        private val dateTv = view.findViewById<TextView>(R.id.dateTv)
        private val homeTeamTv = view.findViewById<TextView>(R.id.homeTeamTv)
        private val awayTeamTv = view.findViewById<TextView>(R.id.awayTeamTv)
        private val homeTeamIv = view.findViewById<ImageView>(R.id.homeTeamIv)
        private val awayTeamIv = view.findViewById<ImageView>(R.id.awayTeamIv)
        private val homeScoreTv = view.findViewById<TextView>(R.id.homeScoreTv)
        private val awayScoreTv = view.findViewById<TextView>(R.id.awayScoreTv)

        fun bindItem(context: Context, favorite: Favorite, listener: (Favorite) -> Unit) {
            nameTv.text = favorite.eventName
            dateTv.text = favorite.eventDate
            homeTeamTv.text = favorite.homeTeamName
            awayTeamTv.text = favorite.awayTeamName
            homeScoreTv.text = favorite.homeScore
            awayScoreTv.text = favorite.awayScore

            GlideApp.with(context)
                .load(Utility.linkJersey(favorite.homeId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(homeTeamIv)

            GlideApp.with(context)
                .load(Utility.linkJersey(favorite.awayId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(awayTeamIv)

            itemView.setOnClickListener {
                listener(favorite)
            }
        }
    }
}