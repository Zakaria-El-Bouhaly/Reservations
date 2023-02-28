package com.example.scheduler.Models

data class Appointment(
    var appId: String,
    var userId: String?,
    val date: String,
    val time: String,
    val description: String,
    val num: String
) {

}