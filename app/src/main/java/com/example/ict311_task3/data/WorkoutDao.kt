package com.example.ict311_task3.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: WorkoutEntity)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(workout: List<WorkoutEntity>)

    @Query("SELECT * FROM workout ORDER BY date ASC")
    fun getAll(): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE id = :id")
    fun getWorkoutById(id: Int): WorkoutEntity?

    @Query("SELECT COUNT(*) from workout")
    fun getCount(): Int

    @Delete
    fun deleteWorkout(selectedWorkouts: List<WorkoutEntity>): Int

    @Delete
    fun deleteWorkoutData(it: WorkoutEntity)



}