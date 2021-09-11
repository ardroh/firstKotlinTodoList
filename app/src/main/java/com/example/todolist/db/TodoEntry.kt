package com.example.todolist.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntry (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val title: String?,
    val isChecked: Boolean
)