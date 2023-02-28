package com.example.scheduler.repositories

import androidx.lifecycle.LiveData
import com.example.scheduler.Models.Appointment

interface AppointmentRepo {
    fun getAllAppoints(): LiveData<List<Appointment>>
    fun upcreateAppoint(app: Appointment): Boolean
    fun deleteAppoint(appId: String): Boolean
}