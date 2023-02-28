package com.example.scheduler


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Models.Appointment
import com.example.scheduler.Models.User
import com.example.scheduler.helpers.Redirect
import com.example.scheduler.viewModels.UserViewModel
import com.example1.projectapp.viewModels.AppointmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson


class HomeActivity : AppCompatActivity() {

    private lateinit var myfab: FloatingActionButton
    private lateinit var appList: RecyclerView

    private lateinit var allapp: ArrayList<Appointment>
    private var user: User? = null
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val i = this.getIntent()
        val userStr: String? = i.getStringExtra("USER")

        if (userStr != null) {
            user = Gson().fromJson(userStr, User::class.java)
            Log.i("N1111", userStr.toString())
        }


        userViewModel = ViewModelProvider(
            this, defaultViewModelProviderFactory
        )[UserViewModel::class.java]

        myfab = findViewById(R.id.fab)
        myfab.setOnClickListener {
            val dialog = AddDialog(false, null)
            dialog.show(supportFragmentManager, null)
        }

        appList = findViewById(R.id.applist)
        val lld: RecyclerView.LayoutManager = LinearLayoutManager(this)
        appList.layoutManager = lld

        allapp = ArrayList<Appointment>()

        val adapter = ListAdapter(this, allapp)
        appList.adapter = adapter

        val appointmentViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        )[AppointmentViewModel::class.java]

        appointmentViewModel.allappoints.observe(this, Observer {
            allapp.clear()
            allapp.addAll(it)
            adapter.notifyDataSetChanged()
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.more_options, menu)
        if (user != null) {
            menu.findItem(R.id.profile).title = user?.name
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.Signout -> {
                userViewModel.logout()
                Redirect.goto(this as Activity, MainActivity())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}