package com.example.scheduler.Models


data class Appointment(
    var appId: String,
    var userId: String?,
    val date: Long,
    val time: Long,
    val description: String,
    val num: String
) {
}