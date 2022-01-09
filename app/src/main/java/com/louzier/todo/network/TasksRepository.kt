package com.louzier.todo.network

import com.louzier.todo.tasklist.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    // Ces deux variables encapsulent la même donnée:
    // [_taskList] est modifiable mais privée donc inaccessible à l'extérieur de cette classe
    // -A partir du TP4, les State Flow sont dans TaskListViewModel
    //private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    // [taskList] est publique mais non-modifiable:
    // On pourra seulement l'observer (s'y abonner) depuis d'autres classes
    // -A partir du TP4, les State Flow sont dans TaskListViewModel
    //public val taskList: StateFlow<List<Task>> = _taskList.asStateFlow()

    suspend fun refresh(): List<Task>? {
        // Call HTTP (opération longue):
        val tasksResponse = tasksWebService.getTasks()
        // À la ligne suivante, on a reçu la réponse de l'API:
        if (tasksResponse.isSuccessful) {
            //Avant TP4
            //val fetchedTasks = tasksResponse.body()
            // on modifie la valeur encapsulée, ce qui va notifier ses Observers et donc déclencher leur callback
            //if (fetchedTasks != null) _taskList.value = fetchedTasks
            return tasksResponse.body()
        }
        return null
    }

    //Avant le TP4
    /*suspend fun createOrUpdate(task: Task) {
        val oldTask = taskList.value.firstOrNull { it.id == task.id }
        val response = when {
            oldTask != null -> tasksWebService.update(task, task.id) //update
            else -> tasksWebService.create(task) // create
        }
        if (response.isSuccessful) {
            val updatedTask = response.body()!!
            if (oldTask != null) _taskList.value = taskList.value - oldTask
            _taskList.value = taskList.value + updatedTask
        }
    }*/

    //Maintenant il faut séparer create et update car on a pas accès à "oldTask" pour déterminer si on update ou create

    suspend fun create(task: Task): Task? {
        var response = tasksWebService.create(task);
        if(response.isSuccessful) {
            return response.body();
        }
        return null;
    }

    suspend fun update(task: Task): Task? {
        var response = tasksWebService.update(task, task.id);
        if(response.isSuccessful) {
            return response.body();
        }
        return null;
    }

    suspend fun delete(task: Task): Boolean {
        val response = tasksWebService.deleteTask(task.id)
        if (response.isSuccessful) {
            //Avant TP4
            //_taskList.value = taskList.value - task
            return true
        }
        return false
    }
}