package com.example.ict311_task3.data

import java.util.*

class SampleDataProvider {

    companion object{
        private val sampleText1 = "Yo YO Yo"
        private val sampleText2 = "Yo \nYO Yo"
        private val sampleText3 = "Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo Yo YO Yo"

    private fun getDate(diff: Long): Date {
        return Date(Date().time + diff)
    }

    fun getWorkout() = arrayListOf(
        WorkoutEntity(getDate(0), sampleText1),
        WorkoutEntity(getDate(1), sampleText2),
        WorkoutEntity(getDate(2), sampleText3)
    )
    }
}