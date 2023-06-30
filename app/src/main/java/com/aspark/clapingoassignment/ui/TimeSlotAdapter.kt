package com.aspark.clapingoassignment.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.clapingoassignment.R
import com.aspark.clapingoassignment.model.TimeSlot
import com.aspark.clapingoassignment.databinding.ItemTimeSlotBinding
import com.aspark.clapingoassignment.databinding.PlaceholderTextBinding

class TimeSlotAdapter(private val timeSlotList: ArrayList<TimeSlot>,
                      private val size: Int):
    RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemTimeSlotBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvTimeSlot = binding.tvTimeSlot
        val cvTimeSlot = binding.cvTimeSlot
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


            val binding = ItemTimeSlotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)

            return ViewHolder( binding )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val context = holder.itemView.context

        if (timeSlotList[position].isBooked) {

            holder.cvTimeSlot.apply {
                setCardBackgroundColor(ColorStateList.valueOf(Color.LTGRAY))
                isClickable = false
            }
        }
        else
            holder
                .cvTimeSlot
                .setCardBackgroundColor(
                    ColorStateList.valueOf(context.resources.getColor(
                        R.color.time_slot_blue, context.theme)))

        holder.tvTimeSlot.text =
            "${timeSlotList[position].startTime}-${timeSlotList[position].endTime}"

    }

    override fun getItemCount(): Int {

       return if (timeSlotList.isEmpty())
                    1
        else
             size.coerceAtMost(timeSlotList.size)
    }


}