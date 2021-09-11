package com.example.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ITodoEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoEntry)

    @Query("DELETE FROM TodoEntry WHERE isChecked = 1")
    suspend fun deleteDone()

    @Query("SELECT * FROM TodoEntry")
    suspend fun getAll(): List<TodoEntry>
}