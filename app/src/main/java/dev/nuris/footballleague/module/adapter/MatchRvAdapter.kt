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
import dev.nuris.footballleague.helper.Utility
import dev.nuris.footballleague.helper.checkNull
import dev.nuris.footballleague.helper.convertGTMFormat
import dev.nuris.footballleague.helper.lib.GlideApp
import dev.nuris.footballleague.model.Event

class MatchRvAdapter (private val context: Context,
                      private val list: List<Event>,
                      private val listener: (Event) -> Unit
) : RecyclerView.Adapter<MatchRvAdapter.ViewHolder>() {

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

        fun bindItem(context: Context, event: Event, listener: (Event) -> Unit) {
            nameTv.text = event.strEvent
            dateTv.text = (event.dateEvent + " " + event.strTime).convertGTMFormat()
            homeTeamTv.text = event.strHomeTeam
            awayTeamTv.text = event.strAwayTeam
            homeScoreTv.text = event.intHomeScore.checkNull()
            awayScoreTv.text = event.intAwayScore.checkNull()

            GlideApp.with(context)
                .load(Utility.linkJersey(event.idHomeTeam!!))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(homeTeamIv)

            GlideApp.with(context)
                .load(Utility.linkJersey(event.idAwayTeam!!))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(awayTeamIv)

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}