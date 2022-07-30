package com.example.a7minutesworkout

import android.app.Application

class WorkoutApp : Application() {
    val db by lazy {
        WorkoutHistoryDatabase.getInstance(this)
    }
}