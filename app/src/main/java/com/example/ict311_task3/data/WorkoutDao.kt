package com.example.ict311_task3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: WorkoutEntity)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(workout: List<WorkoutEntity>)

    @Query("SELECT * FROM workout ORDER BY date ASC")
    fun getAll(): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE id = :id")
    fun getNoteById(id: Int): WorkoutEntity?

    @Query("SELECT COUNT(*) from workout")
    fun getCount(): Int



}