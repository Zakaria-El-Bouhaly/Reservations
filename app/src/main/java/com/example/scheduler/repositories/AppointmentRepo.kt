package com.example.scheduler.repositories

import androidx.lifecycle.LiveData
import com.example.scheduler.Models.Appointment
import kotlinx.coroutines.flow.Flow


interface AppointmentRepo {
    fun getAllAppoints(): LiveData<List<Appointment>>
    fun upcreateAppoint(app: Appointment): Flow<Boolean>
    fun deleteAppoint(appId: String): Flow<Boolean>
}