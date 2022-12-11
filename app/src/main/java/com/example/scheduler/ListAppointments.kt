package com.example.scheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ListAppointments : AppCompatActivity() {
    lateinit var appList:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_appointments)
        appList=findViewById(R.id.applist)



    }
}