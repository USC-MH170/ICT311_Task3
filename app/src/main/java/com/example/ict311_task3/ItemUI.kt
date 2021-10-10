package com.example.ict311_task3

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ict311_task3.databinding.ItemUIFragmentBinding

class ItemUI : Fragment() {

    private lateinit var viewModel: ItemUIViewModel
    private val args: ItemUIArgs by navArgs()
    private lateinit var binding: ItemUIFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }

        setHasOptionsMenu(true)
        requireActivity().title =
            if (args.workoutID == NEW_WORKOUT_ID) {
                getString(R.string.new_workout)
            } else {
                getString(R.string.edit_workout)
            }
        viewModel = ViewModelProvider(this).get(ItemUIViewModel::class.java)
        binding = ItemUIFragmentBinding.inflate(inflater, container, false)
        binding.editor.setText("")

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )

        viewModel.currentWorkout.observe(viewLifecycleOwner, Observer {
            binding.editor.setText(it.title)
        })
        viewModel.getWorkoutById(args.workoutID)


        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndReturn(): Boolean {

        //closes soft keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        //updates the title field
        viewModel.currentWorkout.value?.title = binding.editor.text.toString()
        viewModel.updateWorkout()

        findNavController().navigateUp()
        return true
    }
}