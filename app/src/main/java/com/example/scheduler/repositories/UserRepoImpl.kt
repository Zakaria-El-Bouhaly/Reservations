package com.example.scheduler.repositories

import androidx.lifecycle.MutableLiveData
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.Models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await


class UserRepoImpl : UserRepo {

    private val myAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun login(loginDto: LoginDto): Flow<Boolean> {
        return flow {

            try {
                myAuth.signInWithEmailAndPassword(loginDto.email, loginDto.password).await()
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }.flowOn(Dispatchers.Default)
    }


    override fun register(registerDto: RegisterDto): Flow<Boolean> {
        return flow {
            try {
                var task =
                    myAuth.createUserWithEmailAndPassword(registerDto.email, registerDto.password)
                        .await()
                emit(true);
            } catch (e: Exception) {
                emit(false);
            }
        }.flowOn(Dispatchers.Default)
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
                uid, name, email!!
            )
            user.isAuth = true

            authenticatedUser.value = user
        }

        return authenticatedUser
    }


}