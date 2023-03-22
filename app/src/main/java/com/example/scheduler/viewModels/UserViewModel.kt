package com.example.scheduler.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.Models.User
import com.example.scheduler.repositories.UserRepo
import com.example.scheduler.repositories.UserRepoImpl
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

class UserViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository: UserRepo

    lateinit var currentUser: MutableLiveData<User>

    init {
        repository = UserRepoImpl()
    }

    fun login(loginDto: LoginDto): Flow<Boolean> {
        return repository.login(loginDto)
    }

    fun register(registerDto: RegisterDto): Flow<Boolean> {
        return repository.register(registerDto)
    }

    fun signInWithGoogle(authCred: AuthCredential) {
        currentUser = repository.firebaseLogin(authCred)
    }

    fun logout() {
        currentUser = repository.logout()
    }

    fun getCurrentUser() {
        currentUser = repository.getCurrentUser()
    }
}