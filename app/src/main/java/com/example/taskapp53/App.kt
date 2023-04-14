package com.example.taskapp53

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.taskapp53.database.local.room.TaskDatabase


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        db = databaseBuilder(
            this,
            TaskDatabase::class.java,
            "database"
        )
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        lateinit var db: TaskDatabase
    }
}