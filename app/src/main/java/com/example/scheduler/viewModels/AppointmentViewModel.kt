package com.example1.projectapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.scheduler.Models.Appointment
import com.example.scheduler.repositories.AppointmentRepo
import com.example.scheduler.repositories.AppointmentRepoImpl
import kotlinx.coroutines.flow.Flow

class AppointmentViewModel() : ViewModel() {
    private val repository: AppointmentRepo
    val allappoints: LiveData<List<Appointment>>

    init {
        repository = AppointmentRepoImpl()
        allappoints = getAllAppoints()
    }

    fun getAllAppoints(): LiveData<List<Appointment>> {
        return repository.getAllAppoints()
    }

    fun upcreateAppoint(app: Appointment): Flow<Boolean> {
        return repository.upcreateAppoint(app)
    }

    fun deleteAppoint(id: String): Flow<Boolean>{
        return repository.deleteAppoint(id)
    }
}