package com.louzier.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.louzier.todo.databinding.ActivityMainBinding
import com.louzier.todo.databinding.FragmentTaskListBinding
import com.louzier.todo.databinding.ItemTaskBinding

class MainActivity : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }*/

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

    }
}