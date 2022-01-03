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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TaskListAdapter()
        adapter.taskList = taskList
        recyclerView.adapter = adapter

        val newTaskButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        newTaskButton.setOnClickListener(
            object : View.OnClickListener
            {
                override fun onClick(v : View?) {
                    val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
                    taskList = taskList + newTask
                    adapter.taskList = taskList
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }

}