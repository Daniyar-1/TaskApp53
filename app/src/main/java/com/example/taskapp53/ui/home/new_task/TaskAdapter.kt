package com.example.taskapp53.ui.home.new_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp53.databinding.TaskItemBinding
import com.example.taskapp53.ui.home.TaskModel

class TaskAdapter(
    private var onClick: (Int) -> Unit,
    private var onLongClick: (Int) -> Unit)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = arrayListOf<TaskModel>()

    fun addTask(taskModel: TaskModel) {
        taskList.add(0, taskModel)
    }

    fun addAllTask(list: List<TaskModel>) {
        taskList.clear()
        taskList.addAll(list)
        notifyDataSetChanged()
    }

    fun getTask(position: Int) : TaskModel{
        return taskList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(private var binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taskModel: TaskModel) {
            binding.itemTitle.text = taskModel.title
            binding.itemDesc.text = taskModel.desc

            itemView.setOnClickListener {
               onClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}