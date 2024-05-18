package com.example.vehicletickettermproject.data

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class BusJourney(
    val id: String = "",
    val fromPlace: String = "",
    val toPlace: String = "",
    val beginDateTime: Timestamp? = null,
    val duration: String = "",
    val price: Int = 0,
    val totalSeats: Int = 0,
    val availableSeats: Map<String, Boolean> = emptyMap()
) {
    val isFull: Boolean
        get() = availableSeats.values.none { it }

    fun getFormattedBeginDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return beginDateTime?.let { sdf.format(it.toDate()) } ?: ""
    }

    fun getFormattedBeginDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return beginDateTime?.let { sdf.format(it.toDate()) } ?: ""
    }

    fun getFormattedBeginTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return beginDateTime?.let { sdf.format(it.toDate()) } ?: ""
    }



}