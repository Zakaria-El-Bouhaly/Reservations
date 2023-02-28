package com.example.scheduler.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.Models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class UserRepoImpl : UserRepo {

    private val myAuth: FirebaseAuth = FirebaseAuth.getInstance()

    @OptIn(DelicateCoroutinesApi::class)
    override fun login(loginDto: LoginDto): Boolean {
        var result = false
        val task = myAuth.signInWithEmailAndPassword(loginDto.email, loginDto.password)
        GlobalScope.launch(Dispatchers.IO) {
            task.await()
            result = task.isSuccessful
        }
        return result
    }

    override fun register(registerDto: RegisterDto): Boolean {
        var result = false
        myAuth.createUserWithEmailAndPassword(registerDto.email, registerDto.password)
            .addOnCompleteListener { task ->
                result = task.isSuccessful
            }
        return result
    }

    override fun firebaseLogin(googleAuthCredential: AuthCredential): MutableLiveData<User> {
        val authenticatedUserMutableLiveData = MutableLiveData<User>()

        myAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                authenticatedUserMutableLiveData.value = getCurrentUser().value
            } else {
                authenticatedUserMutableLiveData.value = User()
            }
        }
        return authenticatedUserMutableLiveData
    }

    override fun logout(): MutableLiveData<User> {
        myAuth.signOut()
        return MutableLiveData(User())

    }

    override fun getCurrentUser(): MutableLiveData<User> {
        val authenticatedUser = MutableLiveData<User>()
        authenticatedUser.value = User()
        val firebaseUser: FirebaseUser? = myAuth.currentUser
        if (firebaseUser != null) {
            val uid = firebaseUser.uid
            val name = firebaseUser.displayName ?: "User"
            val email = firebaseUser.email
            val user = User(
                uid,
                name,
                email!!
            )
            user.isAuth = true

            authenticatedUser.value = user
        }

        return authenticatedUser
    }


}