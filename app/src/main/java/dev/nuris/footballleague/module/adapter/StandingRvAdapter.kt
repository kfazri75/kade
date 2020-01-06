package dev.nuris.footballleague.module.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.nuris.footballleague.R
import dev.nuris.footballleague.model.Table
import kotlinx.android.synthetic.main.layout_standing_item.view.*

class StandingRvAdapter (private val list: List<Table>) : RecyclerView.Adapter<StandingRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_standing_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(list[position])

    class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bindItem(table: Table) {
            table.let {
                v.teamNameTv.text = it.name

                v.winTv.text = it.win
                v.drawTv.text = it.draw
                v.lostTv.text = it.loss
                v.totalTv.text = it.total
            }
        }
    }
}