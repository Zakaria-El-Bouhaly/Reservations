package com.example.scheduler.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.Models.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface UserRepo {
    fun login(loginDto: LoginDto):Boolean
    fun register(registerDto: RegisterDto):Boolean
    fun firebaseLogin(googleAuthCredential: AuthCredential): MutableLiveData<User>
    fun logout() : MutableLiveData<User>
    fun getCurrentUser(): MutableLiveData<User>
}