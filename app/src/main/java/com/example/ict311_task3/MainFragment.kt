package com.example.ict311_task3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ict311_task3.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: ListUIAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration (
                context, LinearLayoutManager(context).orientation
                    )
            addItemDecoration(divider)
        }

        viewModel.workoutList.observe(viewLifecycleOwner, Observer {
            Log.i("workoutLogging", it.toString())
            adapter = ListUIAdapter(it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        })

        return binding.root
    }
}