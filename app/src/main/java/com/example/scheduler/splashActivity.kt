package com.example.scheduler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.scheduler.helpers.Redirect
import com.example.scheduler.viewModels.UserViewModel
import com.google.gson.Gson

class splashActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        userViewModel = ViewModelProvider(
            this, defaultViewModelProviderFactory
        )[UserViewModel::class.java]



        Handler(Looper.getMainLooper()).postDelayed({
            checkIfUserIsAuthenticated()
        }, 165);
    }


    private fun checkIfUserIsAuthenticated() {
        userViewModel.getCurrentUser()
        userViewModel.currentUser.observe(this) {
            Log.i("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", it.isAuth.toString())
            if (it.isAuth) {
                Redirect.goto(this as Activity, HomeActivity(), it)
            } else {
                Redirect.goto(this as Activity, MainActivity())
            }
        }
    }
}