package com.example.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TodoEntry::class])
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun getTodoEntryDao() : ITodoEntryDao;
}