package com.example.ict311_task3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict311_task3.data.AppDatabase
import com.example.ict311_task3.data.SampleDataProvider
import com.example.ict311_task3.data.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListUIViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val workoutList = database?.workoutDoa()?.getAll()

    fun addSampleData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sampleWorkout = SampleDataProvider.getWorkout()
                database?.workoutDoa()?.insertAll(sampleWorkout)
            }
        }
    }


}