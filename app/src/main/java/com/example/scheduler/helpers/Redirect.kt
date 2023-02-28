package com.example.scheduler.helpers

import android.app.Activity
import android.content.Intent
import com.example.scheduler.Models.User
import com.google.gson.Gson

class Redirect {
    companion object {
        fun goto(source: Activity, dest: Activity, user: User? = null) {
            val activityIntent = Intent(source, dest::class.java)
            if (user != null) {
                activityIntent.putExtra("USER", Gson().toJson(user))
            }
            source.startActivity(activityIntent)
            source.finish()
        }
    }
}