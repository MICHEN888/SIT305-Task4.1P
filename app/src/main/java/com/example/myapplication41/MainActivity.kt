package com.example.myapplication41

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication41.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(
            onEditClick = { task -> editTask(task) },
            onDeleteClick = { task -> deleteTask(task) }
        )

        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
        }

        binding.buttonAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editTask(task: Task) {
        val intent = Intent(this, AddEditTaskActivity::class.java)
        intent.putExtra("taskId", task.id)
        intent.putExtra("taskTitle", task.title)
        intent.putExtra("taskDescription", task.description)
        intent.putExtra("taskDueDate", task.dueDate)
        startActivity(intent)
    }

    private fun deleteTask(task: Task) {
        taskViewModel.delete(task)
    }
}
