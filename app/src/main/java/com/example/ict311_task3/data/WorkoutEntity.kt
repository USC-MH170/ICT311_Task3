package com.example.ict311_task3.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ict311_task3.NEW_WORKOUT_ID
import java.util.*


@Entity(tableName = "workout")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var date: Date,
    var title: String
        ) {
    constructor(): this(NEW_WORKOUT_ID, Date(), "")
    constructor(date: Date, text: String): this(NEW_WORKOUT_ID,  date, text)
}