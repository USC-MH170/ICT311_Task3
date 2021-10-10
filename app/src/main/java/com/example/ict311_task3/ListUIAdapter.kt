package com.example.ict311_task3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ict311_task3.data.WorkoutEntity
import com.example.ict311_task3.databinding.ListItemBinding

class ListUIAdapter(private val workoutList: List<WorkoutEntity>,
    private val listner: ListItemListener):
    RecyclerView.Adapter<ListUIAdapter.ViewHolder>() {

    val selectedWorkouts = arrayListOf<WorkoutEntity>()

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
        val workout = workoutList[position]
        with(holder.binding) {
            workoutText.text = workout.title
            root.setOnClickListener{
                listner.onItemClick(workout.id)
            }
            fab.setOnClickListener{
                if (selectedWorkouts.contains(workout)) {
                    selectedWorkouts.remove(workout)
                    fab.setImageResource(R.drawable.ic_note)
                } else {
                    selectedWorkouts.add(workout)
                    fab.setImageResource(R.drawable.ic_check)
                }
                listner.onItemSelectionChanged()
            }
            fab.setImageResource(
                if (selectedWorkouts.contains(workout)) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_note
                }
            )
        }
    }

    override fun getItemCount() = workoutList.size


    interface ListItemListener {
        fun onItemClick(workoutID: Int)
        fun onItemSelectionChanged()
    }
}