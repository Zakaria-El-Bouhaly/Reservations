package com.example.scheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Add_appointment : AppCompatActivity() {
    lateinit var myfab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_appointment)
        myfab = findViewById(R.id.fab)
        myfab.setOnClickListener{
            val dialog:AddDialog=AddDialog()
            dialog.show(supportFragmentManager,null)
        }

    }
}