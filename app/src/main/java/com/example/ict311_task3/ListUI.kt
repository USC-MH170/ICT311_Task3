package com.example.ict311_task3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ict311_task3.data.WorkoutEntity
import com.example.ict311_task3.databinding.MainFragmentBinding

class ListUI : Fragment(),
    ListUIAdapter.ListItemListener{

    private lateinit var viewModel: ListUIViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: ListUIAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
        requireActivity().title = getString(R.string.app_display_name)
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListUIViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration (
                context, LinearLayoutManager(context).orientation
                    )
            addItemDecoration(divider)
        }

        viewModel.workoutList?.observe(viewLifecycleOwner, Observer {
            Log.i("workoutLogging", it.toString())
            adapter = ListUIAdapter(it, this@ListUI)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)

            val selectedWorkout = savedInstanceState?.getParcelableArrayList<WorkoutEntity>(SELECTED_WORKOUT_KEY)
            adapter.selectedWorkouts.addAll(selectedWorkout ?: emptyList())



        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId =
            if (this::adapter.isInitialized &&
                    adapter.selectedWorkouts.isNotEmpty()
            ) {
                R.menu.menu_main_selected_items
            } else {
                R.menu.menu_main
            }
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_workout -> deleteWorkout()
            //links to function below which will need updating
            R.id.new_workout -> addNewWorkout()
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addNewWorkout(): Boolean {
        editWorkout(NEW_WORKOUT_ID)
        //below is located in ListUIViewModel + may need to delete when submitting
        //viewModel.addWorkout()
        return true
    }

    private fun deleteWorkout(): Boolean {
        viewModel.deleteWorkout(adapter.selectedWorkouts)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedWorkouts.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)

        return true
    }




    override fun editWorkout(workoutID: Int) {
        Log.i(TAG, "onItemClick: received workout id $workoutID")
        val action = ListUIDirections.actionEditWorkout(workoutID)
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(SELECTED_WORKOUT_KEY, adapter.selectedWorkouts)
        }
        super.onSaveInstanceState(outState)
    }

}