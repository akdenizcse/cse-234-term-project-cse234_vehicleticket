package com.example.vehicletickettermproject.data

data class VTUser(
    val id : String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val reservations : List<Reservation> = emptyList()
)