package com.deecto.autocaller.data

data class CampStatus(
    val answered: Int,
    val completed: Int,
    val rejected: Int,
    val rejectedinbetween: Int,
    val remaining: Int
)