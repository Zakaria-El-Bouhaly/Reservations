package com.example.scheduler.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class AppointmentRepoImpl : AppointmentRepo {

    val database = FirebaseDatabase.getInstance()
    val myauth = FirebaseAuth.getInstance()
    val dbref = database.getReference("appointments")

    override fun getAllAppoints(): LiveData<List<Appointment>> {

        val usersApp = dbref.orderByChild("userId").equalTo(myauth.currentUser?.uid.toString())

        var gson = Gson()

        val liveapps = MutableLiveData<List<Appointment>>()
        val listapps = ArrayList<Appointment>()

        usersApp.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listapps.clear()
                val value = dataSnapshot.children
                for (v in value) {
                    val json = Gson().toJson(v.value)
                    var data = gson.fromJson(json, Appointment::class.java)
                    listapps.add(data!!)
                }

                liveapps.value = listapps
                Log.i("inRepo", listapps.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return liveapps
    }


    override fun upcreateAppoint(app: Appointment): Boolean {
        var result: Boolean = true
        if (app.appId == "") {
            app.appId = dbref.push().key.toString()
        }
        app.userId = myauth.currentUser?.uid

        val row = dbref.child(app.appId).setValue(app)
            .addOnCompleteListener {
                result = true
            }.addOnFailureListener {
                result = false
            }

        return result
    }

    override fun deleteAppoint(appId: String): Boolean {
        var result: Boolean = true
        dbref.child(appId).removeValue().addOnCompleteListener {
            result = true
        }.addOnFailureListener {
            result = false
        }
        return result
    }

}