package com.louzier.todo.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.louzier.todo.R
import com.louzier.todo.databinding.ActivityFormBinding
import com.louzier.todo.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val task = intent.getSerializableExtra("task") as Task?
        if (task != null)
        {
            binding.editTitle.setText(task.title)
            binding.editDescription.setText(task.description)
        }

        val importText = intent.getSerializableExtra("importText") as String?
        if (importText != null)
        {
            binding.editDescription.setText(importText)
        }

        binding.validateFormButton.setOnClickListener {
            val id = task?.id ?: UUID.randomUUID().toString()
            val newTask = Task(id = id, title = binding.editTitle.text.toString(), description = binding.editDescription.text.toString())
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}