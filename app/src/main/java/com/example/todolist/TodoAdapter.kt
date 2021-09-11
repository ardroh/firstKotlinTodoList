package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var todos: MutableList<Todo> = mutableListOf()
    private lateinit var onTodoCheckedCallback : (todo: Todo) -> Unit;

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun updateTodos(updatedTodos: MutableList<Todo>) {
        this.todos = updatedTodos
        notifyDataSetChanged()
    }

    fun setOnTodoChecked(callback: (todo: Todo) -> Unit) {
        this.onTodoCheckedCallback = callback
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            val curTodo = todos[position]
            tvTodoTitle.text = curTodo.title
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked == curTodo.isChecked) {
                    return@setOnCheckedChangeListener
                }
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = isChecked
                onTodoCheckedCallback(curTodo)
            }
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}