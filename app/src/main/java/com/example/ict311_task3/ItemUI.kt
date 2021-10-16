package com.example.ict311_task3

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog.show
import android.os.Build
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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ict311_task3.data.DateConverter
import com.example.ict311_task3.databinding.ItemUIFragmentBinding
import kotlinx.android.synthetic.main.item_u_i_fragment.view.*
import java.time.LocalDate
import java.time.LocalDate.parse
import java.util.*
import java.util.Date.parse
import kotlin.math.log


class ItemUI : Fragment(), DatePickerFragment.Callbacks {

    private lateinit var viewModel: ItemUIViewModel
    private val args: ItemUIArgs by navArgs()
    private lateinit var binding: ItemUIFragmentBinding




    @RequiresApi(Build.VERSION_CODES.O)
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

        /*
        viewModel = Data and binding = XML references
        */
        viewModel = ViewModelProvider(this).get(ItemUIViewModel::class.java)
        binding = ItemUIFragmentBinding.inflate(inflater, container, false)
        /*
                On Click Liseners
         */
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


        /*
        Back Button Request
        */
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )
        /*
        Observes the viewmodel
        */
        viewModel.currentWorkout.observe(viewLifecycleOwner, Observer {
            binding.group.apply {isChecked = viewModel.currentWorkout.value?.group == true }

            val savedTitleString = savedInstanceState?.getString(TITLE_TEXT_KEY)
            val savedPlaceString = savedInstanceState?.getString(PLACE_TEXT_KEY)
            val savedStartString = savedInstanceState?.getString(START_TEXT_KEY)
            val savedFinishString = savedInstanceState?.getString(FINISH_TEXT_KEY)
            val savedDateString = savedInstanceState?.getCharSequence(DATE_TEXT_KEY)
            val savedGroupBoolean = savedInstanceState?.getBoolean(GROUP_TEXT_KEY)
            binding.date.setText (DateFormat.format(DATE_FORMAT, viewModel.currentWorkout.value?.date).toString())
            binding.title.setText(savedTitleString ?: it.title)
            binding.place.setText(savedPlaceString ?: it.place)
            binding.start.setText(savedStartString ?: it.start)
            binding.finish.setText(savedFinishString ?: it.finish)
            binding.group.apply {
                if (savedGroupBoolean != null) {
                    isChecked = savedGroupBoolean
                }
            }
        })



        viewModel.getWorkoutById(args.workoutID)
        return binding.root
    }


    /*
    Saves instance for rotate
     */
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding) {
            outState.putString(TITLE_TEXT_KEY, title.text.toString())
            outState.putString(PLACE_TEXT_KEY, place.text.toString())
            outState.putString(START_TEXT_KEY, start.text.toString())
            outState.putString(FINISH_TEXT_KEY, finish.text.toString())
            outState.putBoolean(GROUP_TEXT_KEY, binding.group.isChecked())
        }
        super.onSaveInstanceState(outState)
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
        viewModel.currentWorkout.value?.group = binding.group.isChecked
        viewModel.updateWorkout()
        Toast.makeText(context, "Workout Saved", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
        return true
    }

    override fun onDateSelected(date: Date) {
        viewModel.currentWorkout.value?.date = date
        binding.date.text = DateFormat.format(DATE_FORMAT, viewModel.currentWorkout.value?.date).toString()
        viewModel.updateWorkout()
    }
}