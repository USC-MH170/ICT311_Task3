package com.example.ict311_task3.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ict311_task3.NEW_WORKOUT_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "workout")
data class WorkoutEntity (
@PrimaryKey(autoGenerate = true)
var id:Int,
var date: Date,
var title: String,
var place: String,
var start: Int,
var finish: Int,
var group: Boolean
) : Parcelable {
    constructor(): this(NEW_WORKOUT_ID, Date(), "", "", 0, 0, false)
    constructor(date: Date, title: String, place: String, start: Int, finish: Int, group: Boolean ): this(NEW_WORKOUT_ID,  date, title, place, start, finish, group)
}