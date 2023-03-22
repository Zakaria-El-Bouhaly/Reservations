package com.example.scheduler.repositories

import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.Models.User
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun login(loginDto: LoginDto): Flow<Boolean>
    fun register(registerDto: RegisterDto):Flow<Boolean>
    fun firebaseLogin(googleAuthCredential: AuthCredential): MutableLiveData<User>
    fun logout() : MutableLiveData<User>
    fun getCurrentUser(): MutableLiveData<User>
}