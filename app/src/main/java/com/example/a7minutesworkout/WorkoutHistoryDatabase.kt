package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class WorkoutHistoryDatabase : RoomDatabase() {

    abstract fun historyDao():HistoryDao

    companion object{

        @Volatile
        private var INSTANCE : WorkoutHistoryDatabase? = null

        fun getInstance(context : Context) : WorkoutHistoryDatabase {

            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutHistoryDatabase::class.java,
                        "workout_history_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}