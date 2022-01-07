package com.louzier.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.louzier.todo.databinding.ActivityMainBinding
import com.louzier.todo.tasklist.TaskListFragment

class MainActivity : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }*/

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let { text ->
                        mainBinding = ActivityMainBinding.inflate(layoutInflater)
                        setContentView(mainBinding.root)
                        val taskListFragment : TaskListFragment = supportFragmentManager.findFragmentById(R.id.fragment_tasklist) as TaskListFragment
                        taskListFragment.createNewTask(text)
                    }

                }
            }
            else -> {
                mainBinding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(mainBinding.root)
            }
        }
    }
}