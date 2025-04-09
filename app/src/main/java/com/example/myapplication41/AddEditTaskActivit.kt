package com.example.myapplication41

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication41.databinding.ActivityAddEditTaskBinding

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // 检查是否是编辑任务
        taskId = intent.getIntExtra("taskId", -1).takeIf { it != -1 }

        if (taskId != null) {
            binding.editTextTitle.setText(intent.getStringExtra("taskTitle"))
            binding.editTextDescription.setText(intent.getStringExtra("taskDescription"))
            binding.editTextDueDate.setText(intent.getStringExtra("taskDueDate"))
        }

        binding.buttonSaveTask.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val dueDate = binding.editTextDueDate.text.toString()

            if (title.isNotEmpty() && dueDate.isNotEmpty()) {
                val task = Task(
                    id = taskId ?: 0,
                    title = title,
                    description = description,
                    dueDate = dueDate
                )
                if (taskId != null) {
                    taskViewModel.update(task)
                    Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                } else {
                    taskViewModel.insert(task)
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
