package com.example.ict311_task3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ict311_task3.data.SampleDataProvider
import com.example.ict311_task3.data.WorkoutEntity

class ListUIViewModel : ViewModel() {

    val workoutList = MutableLiveData<List<WorkoutEntity>>()

    init {
        workoutList.value = SampleDataProvider.getWorkout()
    }


}