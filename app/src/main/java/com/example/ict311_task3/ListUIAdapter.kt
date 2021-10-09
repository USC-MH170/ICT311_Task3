package com.example.ict311_task3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ict311_task3.data.WorkoutEntity
import com.example.ict311_task3.databinding.ListItemBinding

class ListUIAdapter(private val workoutList: List<WorkoutEntity>):
    RecyclerView.Adapter<ListUIAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView){
            val binding = ListItemBinding.bind(itemView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout =workoutList[position]
        with(holder.binding) {
            workoutText.text = workout.text
        }
    }

    override fun getItemCount() = workoutList.size
}