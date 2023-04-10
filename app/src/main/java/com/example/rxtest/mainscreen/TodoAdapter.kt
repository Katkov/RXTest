package com.example.rxtest.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rxtest.databinding.ItemTodoBinding
import com.example.rxtest.networking.model.Todo

class TodoAdapter(private val navigate: (Todo) -> Unit) :
    ListAdapter<Todo, TodoAdapter.TodoHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        return TodoHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    inner class TodoHolder(private val itemBinding: ItemTodoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(todo: Todo) {
            itemBinding.apply {
                itemTodoName.text = todo.text
                itemTodoIsDone.isChecked = todo.isDone
                root.setOnClickListener { navigate.invoke(todo) }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}