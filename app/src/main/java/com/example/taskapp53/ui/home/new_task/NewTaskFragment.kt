package com.example.taskapp53.ui.home.new_task

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskapp53.App
import com.example.taskapp53.database.remote.FirebaseUtils
import com.example.taskapp53.databinding.FragmentNewTaskBinding
import com.example.taskapp53.extensions.showToast
import com.example.taskapp53.ui.home.HomeFragment.Companion.EDIT_KEY
import com.example.taskapp53.ui.home.TaskModel

class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var task: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)

        initViews()
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            if (arguments != null) {
                updateData(task)
            } else {
                saveDataToRoom()
                saveDataToFirestore()
            }
            findNavController().navigateUp()
        }
    }

    private fun initViews() {
        if (arguments != null) {
            binding.btnSave.text = "Update"
            task = arguments?.getSerializable(EDIT_KEY) as TaskModel
            binding.etTitle.setText(task.title)
            binding.etDesc.setText(task.desc)
        } else {
            binding.btnSave.text = "Save"
        }
    }

    private fun saveDataToRoom() {
        App.db.taskDao().insert(
            TaskModel(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString()
            )
        )

        Log.e("ololo", "Заметка создана: " + binding.etTitle.text.toString())

    }
    private fun saveDataToFirestore(){
        val task = TaskModel(
            title = binding.etTitle.text.toString().trim(),
            desc = binding.etDesc.text.toString().trim()
        )

        FirebaseUtils().firestoreDB.collection("tasks")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d("ololo", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("ololo", "Error adding document", e)
            }
    }

    private fun updateData(taskModel: TaskModel) {
        taskModel.title = binding.etTitle.text.toString()
        taskModel.desc = binding.etDesc.text.toString()
        App.db.taskDao().update(taskModel)
    }

    companion object {
        const val NEW_TASK_KEY = "new_task"
    }
}