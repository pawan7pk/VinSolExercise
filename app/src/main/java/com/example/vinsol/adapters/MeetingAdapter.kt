package com.example.vinsol.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vinsol.R
import com.example.vinsol.model.ScheduleMeeting
import kotlinx.android.synthetic.main.item_schedule_meeting.view.*

class MeetingAdapter(private var meeting: ArrayList<ScheduleMeeting>) :
    RecyclerView.Adapter<MeetingAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_schedule_meeting, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return meeting.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scheduleMeeting: ScheduleMeeting = meeting[position]
        holder.itemView.tv_date.text = scheduleMeeting.start_time + " - " + scheduleMeeting.end_time
        holder.itemView.tv_description.text = scheduleMeeting.description
    }


}