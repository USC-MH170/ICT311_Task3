package com.example.ict311_task3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ict311_task3.databinding.MainFragmentBinding
import kotlin.math.log

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

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListUIViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration (
                context, LinearLayoutManager(context).orientation
                    )
            addItemDecoration(divider)
        }

        viewModel.workoutList.observe(viewLifecycleOwner, Observer {
            Log.i("workoutLogging", it.toString())
            adapter = ListUIAdapter(it, this@ListUI)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        })

        return binding.root
    }

    override fun onItemClick(workoutID: Int) {
        Log.i(TAG, "onItemClick: received workout id $workoutID")
        val action = ListUIDirections.actionEditWorkout(workoutID)
        findNavController().navigate(action)
    }
}