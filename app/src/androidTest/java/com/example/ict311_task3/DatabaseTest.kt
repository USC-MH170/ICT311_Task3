package com.example.ict311_task3

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ict311_task3.data.AppDatabase
import com.example.ict311_task3.data.SampleDataProvider
import com.example.ict311_task3.data.WorkoutDao
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: WorkoutDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.workoutDoa()!!
    }

    @Test
    fun createWorkout() {
        dao.insertAll(SampleDataProvider.getWorkout())
        val count = dao.getCount()
        assertEquals(count, SampleDataProvider.getWorkout().size)
    }

    @After
    fun closeDb() {
        database.close()
    }




}