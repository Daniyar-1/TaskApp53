package com.example.taskapp53.database.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp53.ui.home.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}