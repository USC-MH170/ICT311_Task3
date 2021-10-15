package com.example.ict311_task3

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ict311_task3.data.DateConverter
import com.example.ict311_task3.databinding.ItemUIFragmentBinding
import java.util.*
import kotlin.math.log


class ItemUI : Fragment(), DatePickerFragment.Callbacks {

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
        //Sets the Fragment Title
        requireActivity().title =
            if (args.workoutID == NEW_WORKOUT_ID) {
                getString(R.string.new_workout)
            } else {
                getString(R.string.edit_workout)
            }
        viewModel = ViewModelProvider(this).get(ItemUIViewModel::class.java)
        binding = ItemUIFragmentBinding.inflate(inflater, container, false)
        binding.title.setText("")
        binding.place.setText("")
        binding.start.setText("")
        binding.finish.setText("")
        binding.date.setOnClickListener {
            DatePickerFragment().apply {
                viewModel.currentWorkout.value?.let { it ->
                    DatePickerFragment.newInstance(it.date).apply {
                        setTargetFragment(this@ItemUI, REQUEST_DATE)
                        show(this@ItemUI.parentFragmentManager, DIALOG_DATE)
                    }
                }
            }
        }

        binding.group.setOnClickListener {
            viewModel.currentWorkout.value?.group = viewModel.currentWorkout.value?.group != true

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )

        viewModel.currentWorkout.observe(viewLifecycleOwner, Observer {
            val savedTitleString = savedInstanceState?.getString(TITLE_TEXT_KEY)
            val savedString = savedInstanceState?.getString(WORKOUT_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            binding.title.setText(savedTitleString ?: it.title)
            binding.place.setText(savedTitleString ?: it.place)
            binding.start.setText(savedString ?: it.start)
            binding.finish.setText(savedString ?: it.finish)
            binding.group.apply {isChecked = viewModel.currentWorkout.value?.group == true }
            binding.date.setText (DateFormat.format(DATE_FORMAT, viewModel.currentWorkout.value?.date).toString())
            binding.title.setSelection(cursorPosition)
            binding.place.setSelection(cursorPosition)
            binding.start.setSelection(cursorPosition)
            binding.finish.setSelection(cursorPosition)
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

        //updates the text fields
        viewModel.currentWorkout.value?.title = binding.title.text.toString()
        viewModel.currentWorkout.value?.place = binding.place.text.toString()
        viewModel.currentWorkout.value?.start = binding.start.text.toString()
        viewModel.currentWorkout.value?.finish = binding.finish.text.toString()
        viewModel.updateWorkout()

        findNavController().navigateUp()
        return true
    }










    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.title) {
            outState.putString(TITLE_TEXT_KEY, binding.title.text.toString())

            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        with(binding.place) {
            outState.putString(WORKOUT_TEXT_KEY, binding.title.text.toString())

            outState.putInt(CURSOR_POSITION_KEY2, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }















    override fun onDateSelected(date: Date) {
        viewModel.currentWorkout.value?.date = date
        binding.date.text = DateFormat.format(DATE_FORMAT, viewModel.currentWorkout.value?.date).toString()
    }

}