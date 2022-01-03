package com.louzier.todo.tasklist

import android.os.Bundle
import android.os.Debug
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.louzier.todo.R
import com.louzier.todo.databinding.ActivityMainBinding
import com.louzier.todo.databinding.FragmentTaskListBinding
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    private var _binding: FragmentTaskListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_task_list, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_main_recycler_view)
        val recyclerView = binding.fragmentMainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TaskListAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(taskList)
        adapter.onClickDelete = { task ->
            val toRemove = taskList.indexOf(task)
            taskList = taskList.subList(0,toRemove) + taskList.subList(toRemove+1,taskList.size)
            adapter.submitList(taskList)
            adapter.notifyDataSetChanged()
        }

        //val newTaskButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val newTaskButton = binding.floatingActionButton
        newTaskButton.setOnClickListener(
            object : View.OnClickListener
            {
                override fun onClick(v : View?) {
                    val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
                    taskList = taskList + newTask
                    adapter.submitList(taskList)
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }

}