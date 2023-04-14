package com.example.taskapp53.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp53.App
import com.example.taskapp53.R
import com.example.taskapp53.database.remote.FirebaseUtils
import com.example.taskapp53.databinding.FragmentHomeBinding
import com.example.taskapp53.ui.home.new_task.TaskAdapter
import com.google.firebase.firestore.ktx.toObject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskAdapter: TaskAdapter
    private var listOfDataFirestore = arrayListOf<TaskModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        initViews()
        initListeners()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = TaskAdapter(this::onClick, this::onLongClickListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort) {
            val items = arrayOf("Date", "A-z", "z-A")
            val builder = AlertDialog.Builder(requireContext())
            with(builder)
            {
                setTitle("Sort by...:")
                setItems(items) { dialog, which ->
                    when (which) {
                        0 -> {
                            taskAdapter.addAllTask(
                                App.db.taskDao().getAllTasksByDate() as List<TaskModel>
                            )
                            dialog.dismiss()
                        }
                        1 -> {
                            taskAdapter.addAllTask(
                                App.db.taskDao().getAllTasksByAZ() as List<TaskModel>
                            )
                            dialog.dismiss()

                        }
                        2 -> {
                            taskAdapter.addAllTask(
                                App.db.taskDao().getAllTasksByZA() as List<TaskModel>
                            )
                            dialog.dismiss()
                        }

                    }
                }
                show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }

    private fun initViews() {
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = taskAdapter

//        getData()
        getDataFirestore()

    }

    private fun getData() {
        taskAdapter.addAllTask(App.db.taskDao().getAllTasks() as List<TaskModel>)
    }

    private fun getDataFirestore(){
        FirebaseUtils().firestoreDB.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(TaskModel::class.java)
                    listOfDataFirestore.add(data)
                    Log.d("ololo", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ololo", "Error getting documents.", exception)
            }
        taskAdapter.addAllTask(listOfDataFirestore)
        listOfDataFirestore.clear()

    }

    private fun onClick(pos: Int) {
        val task = taskAdapter.getTask(pos)
        findNavController().navigate(R.id.newTaskFragment, bundleOf(EDIT_KEY to task))
    }

    private fun onLongClickListener(pos: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Deleting...")
        builder.setMessage("Do you want to delete " + taskAdapter.getTask(pos).title)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            App.db.taskDao().delete(taskAdapter.getTask(pos))
            getData()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    companion object{
        const val EDIT_KEY = "EDIT"
    }
}