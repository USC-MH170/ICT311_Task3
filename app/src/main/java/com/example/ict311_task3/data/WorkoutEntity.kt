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
    var title: String
        ) : Parcelable {
    constructor(): this(NEW_WORKOUT_ID, Date(), "")
    constructor(date: Date, text: String): this(NEW_WORKOUT_ID,  date, text)
}