package com.louzier.todo.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louzier.todo.network.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {

    // Repository pour les requêtes HTTP à l'API
    private val repository = TasksRepository()


    // La liste de tâche enregistré comme state flows.
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    fun refresh() {
        viewModelScope.launch {
            // Récupère la liste de tâches
            var taskList = repository.refresh()

            // Si refresh réussit, modifie tasklist value. En cas d'échec (null), laisse l'anciennce valeur
            if (taskList != null) {
                _taskList.value = taskList
            }
        }
    }

    fun createOrUpdate(task: Task) {
        viewModelScope.launch {
            val oldTask = taskList.value.firstOrNull { it.id == task.id }
            // Selon si la tâche existe déjà ou non, on la crée ou on l'update
            val task = when {
                oldTask != null -> repository.update(task);
                else -> repository.create(task);
            }
            if(task != null) {
                if(oldTask != null) {
                    _taskList.value = taskList.value - oldTask;
                }
                _taskList.value = taskList.value + task;
            }
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            // Si la requête échoue, repository.delete retourne false
            if (repository.delete(task)) {
                _taskList.value = taskList.value - task;
            }
        }
    }
}