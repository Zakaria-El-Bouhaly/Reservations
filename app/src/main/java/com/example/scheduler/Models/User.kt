package com.example.scheduler.Models

import  java.io.Serializable

data class User(
    val uid: String?="",
    val name: String?="",
    val email: String?=""
) : Serializable {
    var isAuth: Boolean = false
    var isManager: Boolean=false

}