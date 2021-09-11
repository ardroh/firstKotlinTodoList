package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.ITodoEntryDao
import com.example.todolist.db.TodoEntry
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(private val todoDao: ITodoEntryDao) : ViewModel() {
    private val todos: MutableLiveData<List<Todo>> by lazy {
        MutableLiveData<List<Todo>>().also {
            loadTodos()
        }
    }

    fun getTodos(): LiveData<List<Todo>> {
        return todos
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            val todoEntry = TodoEntry(id=todo.id, title = todo.title, isChecked = todo.isChecked)
            runBlocking {
                todoDao.insert(todoEntry)
                loadTodos()
            }
        }
    }

    fun removeCompletedTodos() {
        viewModelScope.launch {
            todoDao.deleteDone()
            loadTodos()
        }
    }

    private fun loadTodos() {
        viewModelScope.launch {
            val converted = todoDao.getAll().map { todoEntry -> Todo(todoEntry.id, todoEntry.title.toString(), todoEntry.isChecked)}
            todos.postValue(converted)
        }
    }
}