package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolist.db.TodoListDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter;
    lateinit var db: TodoListDatabase;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(
            applicationContext,
            TodoListDatabase::class.java, "todo-name"
        ).build()
        todoAdapter = TodoAdapter()
        val model = MainViewModel(db.getTodoEntryDao())
        model.getTodos().observe(this, Observer<List<Todo>>{ todos -> todoAdapter.updateTodos(todos as MutableList<Todo>) })
        todoAdapter.setOnTodoChecked { todo -> model.addTodo(todo) }

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(title = todoTitle)
                runBlocking {
                    launch {
                        model.addTodo(todo)
                    }
                }
                etTodoTitle.text.clear()
            }
        }

        btnDeleteDoneTodos.setOnClickListener {
            model.removeCompletedTodos()
        }
    }
}