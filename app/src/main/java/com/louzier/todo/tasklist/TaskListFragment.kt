package com.louzier.todo.tasklist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.louzier.todo.databinding.FragmentTaskListBinding
import com.louzier.todo.databinding.ItemTaskBinding
import com.louzier.todo.form.FormActivity
import com.louzier.todo.network.Api
import com.louzier.todo.network.TasksRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaskListFragment : Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2")
    )

    private val tasksRepository = TasksRepository()

    private val adapterListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            lifecycleScope.launch {
                tasksRepository.delete(task)
            }
            updateList()
        }

        override fun onClickEdit(task: Task) {
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("task", task)
            formLauncher.launch(intent)
        }

        override fun onLongClickShare(task: Task) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, task.description)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private val adapter = TaskListAdapter(adapterListener)

    val formLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val newTask = result.data?.getSerializableExtra("task") as Task

        lifecycleScope.launch {
            tasksRepository.createOrUpdate(newTask)
        }

        updateList()
    }

    private fun updateList()
    {
        lifecycleScope.launch {
            tasksRepository.taskList.collect { newList ->
                adapter.submitList(newList)
            }
        }
    }


    private var _fragmentTaskListBinding: FragmentTaskListBinding? = null
    private val fragmentTaskListBinding get() = _fragmentTaskListBinding!!

    private var _itemTaskBinding: ItemTaskBinding? = null
    private val itemTaskBinding get() = _itemTaskBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _itemTaskBinding = ItemTaskBinding.inflate(inflater, container, false)
        _fragmentTaskListBinding = FragmentTaskListBinding.inflate(inflater, container, false)

        /*
        DEPRECATED : implémentation du tp 2 pour pouvoir changer la rotation du téléphone inutile depuis
        la synchronisation avec la base de donnée en ligne
        val jsonList = savedInstanceState?.getSerializable("taskList") as String?
        if (jsonList != null)
        {
            taskList = Json.decodeFromString(jsonList)
        }
        */

        return fragmentTaskListBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTaskListBinding = null
        _itemTaskBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = fragmentTaskListBinding.fragmentMainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        /*
        DEPRECATED : implémentation du tp 2 pour pouvoir changer la rotation du téléphone inutile depuis
        la synchronisation avec la base de donnée en ligne
        val jsonList = savedInstanceState?.getSerializable("taskList") as String?
        if (jsonList != null)
        {
            taskList = Json.decodeFromString(jsonList)
        }
        */


        updateList()

        val newTaskButton = fragmentTaskListBinding.floatingActionButton
        newTaskButton.setOnClickListener {
            formLauncher.launch(
                Intent(
                    context,
                    FormActivity::class.java
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            fragmentTaskListBinding.userInfoTextView.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        lifecycleScope.launch {
            tasksRepository.refresh() // on demande de rafraîchir les données sans attendre le retour directement
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /*
        DEPRECATED : implémentation du tp 2 pour pouvoir changer la rotation du téléphone inutile depuis
        la synchronisation avec la base de donnée en ligne
        val jsonList = Json.encodeToString(taskList)
        outState.putSerializable("taskList", jsonList)
        */
    }

    public fun createNewTask(string: String)
    {
        val intent = Intent(context, FormActivity::class.java)
        intent.putExtra("importText", string)
        formLauncher.launch(intent)
    }
}