package com.example.taskapp53.database.local.room

import androidx.room.*
import com.example.taskapp53.ui.home.TaskModel

@Dao
interface TaskDao {

    @Insert
    fun insert(task: TaskModel?)

    @Delete
    fun delete(task: TaskModel?)

    @Update
    fun update(task: TaskModel?)

    @Query("SELECT * FROM TaskModel")
    fun getAllTasks(): List<TaskModel?>

    @Query("SELECT * FROM TaskModel ORDER BY id DESC")
    fun getAllTasksByDate(): List<TaskModel?>

    @Query("SELECT * FROM TaskModel ORDER BY title ASC")
    fun getAllTasksByAZ(): List<TaskModel?>

    @Query("SELECT * FROM TaskModel ORDER BY title DESC")
    fun getAllTasksByZA(): List<TaskModel?>
}