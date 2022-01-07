package com.louzier.todo.tasklist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.louzier.todo.R
import com.louzier.todo.databinding.ActivityMainBinding
import com.louzier.todo.databinding.ItemTaskBinding
import com.louzier.todo.form.FormActivity

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.title == newItem.title && oldItem.description == newItem.description
}

interface TaskListListener {
    fun onClickDelete(task: Task)
    fun onClickEdit(task: Task)
    fun onLongClickShare(task: Task)
}

class TaskListAdapter(val listener: TaskListListener) : androidx.recyclerview.widget.ListAdapter<Task,TaskListAdapter.TaskViewHolder>(TaskDiffCallback) {

    // Déclaration de la variable lambda dans l'adapter:
    //var onClickDelete: (Task) -> Unit = {}
    //var onClickEdit: (Task) -> Unit = {}

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(private val itemBinding : ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task) {

            //val textView = itemView.findViewById<TextView>(R.id.task_title)
            val textView = itemBinding.taskTitle
            textView.text = task.title
            //val description = itemView.findViewById<TextView>(R.id.task_description)
            val description = itemBinding.taskDescription
            description.text = task.description

            itemBinding.deleteTaskButton.setOnClickListener {
                listener.onClickDelete(task)
            }
            itemBinding.editTaskButton.setOnClickListener {
                listener.onClickEdit(task)
            }
            itemBinding.taskLayout.setOnLongClickListener {
                listener.onLongClickShare(task)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}