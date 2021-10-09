package com.example.ict311_task3.data

import com.example.ict311_task3.NEW_WORKOUT_ID
import java.util.*

data class WorkoutEntity (
    var id:Int,
    var date: Date,
    var text: String
        ) {
    constructor(): this(NEW_WORKOUT_ID, Date(), "")
    constructor(date: Date, text: String): this(NEW_WORKOUT_ID,  date, text)
}