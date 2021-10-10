package com.example.ict311_task3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict311_task3.data.AppDatabase
import com.example.ict311_task3.data.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemUIViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AppDatabase.getInstance(app)
    val currentWorkout = MutableLiveData<WorkoutEntity>()

    fun getWorkoutById(workoutId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val workout =
                    if ( workoutId != NEW_WORKOUT_ID) {
                        database?.workoutDoa()?.getWorkoutById(workoutId)
                    } else {
                        WorkoutEntity()
                    }
                currentWorkout.postValue(workout)
            }
        }
    }

}