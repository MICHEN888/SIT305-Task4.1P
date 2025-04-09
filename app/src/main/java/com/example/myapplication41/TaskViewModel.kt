package com.example.myapplication41

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDao.insertTask(task)
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDao.updateTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDao.deleteTask(task)
    }
}
