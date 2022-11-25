package com.deecto.autocaller.data

data class CurrentCamp(
    val answered: List<String>,
    val completedcalls: List<String>,
    val rejected: List<String>,
    val rejetinbetween: List<String>,
    val remainingcalls: List<String>
)