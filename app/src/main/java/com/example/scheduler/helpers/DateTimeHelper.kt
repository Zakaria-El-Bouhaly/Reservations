package com.example.scheduler.helpers

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.util.*

class DateTimeHelper {
    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        fun getHour(time: Long): String {
            val date = Date(time)
            val formatter = SimpleDateFormat("HH")
            return formatter.format(date)
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun getMinute(time: Long): String {
            val date = Date(time)
            val formatter = SimpleDateFormat("mm")
            return formatter.format(date)
        }


        @RequiresApi(Build.VERSION_CODES.N)
        fun getHourMinute(time: Long): String {
            val date = Date(time)
            val formatter = SimpleDateFormat("HH:mm")
            return formatter.format(date)
        }

        fun getFormattedDate(date: Long): String {
            return DateFormat.format("dd/MM/yyyy", Date(date)).toString()
        }
    }
}