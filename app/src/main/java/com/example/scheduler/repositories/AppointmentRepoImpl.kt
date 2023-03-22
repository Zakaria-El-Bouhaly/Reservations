package com.example.scheduler.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AppointmentRepoImpl : AppointmentRepo {

    val database = FirebaseDatabase.getInstance()
    val myauth = FirebaseAuth.getInstance()
    val dbref = database.getReference("appointments")

    override fun getAllAppoints(): LiveData<List<Appointment>> {

        val usersApp = dbref.orderByChild("userId").equalTo(myauth.currentUser?.uid.toString())
        val gson = Gson()
        val liveapps = MutableLiveData<List<Appointment>>()
        val listapps = ArrayList<Appointment>()

        usersApp.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listapps.clear()
                val value = dataSnapshot.children
                for (v in value) {
                    val json = Gson().toJson(v.value)
                    val data = gson.fromJson(json, Appointment::class.java)
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


    override fun upcreateAppoint(app: Appointment): Flow<Boolean> {
        return flow {
            try {
                if (app.appId == "") {
                    app.appId = dbref.push().key.toString()
                }
                app.userId = myauth.currentUser?.uid

                val task = dbref.child(app.appId).setValue(app).await()

                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }.flowOn(Dispatchers.Default)
    }

    override fun deleteAppoint(appId: String): Flow<Boolean> {
        return flow {
            try {
                val result = dbref.child(appId).removeValue().await()
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }
    }

}