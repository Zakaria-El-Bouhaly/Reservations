package com.example.scheduler.Models

import  java.io.Serializable

data class LoginDto(
    val email: String,
    val password: String
) : Serializable {

}

data class RegisterDto(
    val email: String,
    val password: String,
    val passConf: String?
) : Serializable {

}