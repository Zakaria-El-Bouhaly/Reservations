package com.example.scheduler


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Models.Appointment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson


class HomeActivity : AppCompatActivity() {

    lateinit var myfab: FloatingActionButton
    lateinit var appList: RecyclerView

    lateinit var database: FirebaseDatabase
    lateinit var dbref: DatabaseReference
    lateinit var myauth: FirebaseAuth
    lateinit var allapp: ArrayList<Appointment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        myfab = findViewById(R.id.fab)
        myfab.setOnClickListener {
            val dialog: AddDialog = AddDialog(false, null)
            dialog.show(supportFragmentManager, null)
        }

        appList = findViewById(R.id.applist)
        val lld: RecyclerView.LayoutManager = LinearLayoutManager(this)
        appList.layoutManager = lld




        database = FirebaseDatabase.getInstance()
        myauth = FirebaseAuth.getInstance()
        dbref = database.getReference("appointments")

        val usersApp = dbref



        var gson = Gson()

        usersApp.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.children
                allapp = ArrayList()

                for (v in value) {
                    val json = Gson().toJson(v.value)

                    var data = gson?.fromJson(json, Appointment::class.java)

                    Log.d("BASED", "Value is: $json")
//                    val app = Appointment(
//                        v.key.toString(),
//                        v.child("userId").value.toString(),
//                        v.child("date").value.toString(),
//                        v.child("time").value.toString(),
//                        v.child("description").value.toString(),
//                        v.child("num").value.toString()
//                    )
                    allapp.add(data!!)
                }
                appList.adapter = ListAdapter(this@HomeActivity, allapp)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("BASED", "Failed to read value.", error.toException())
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.more_options, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.Signout -> {
                myauth.signOut()
                intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}