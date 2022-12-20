package com.example.scheduler.Models

data class Appointment(
    val appId: String,
    val userId: String?,
    val date: String,
    val time: String,
    val description: String,
    val num: String
) {

}