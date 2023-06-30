package com.aspark.clapingoassignment.model

data class TimeSlot(
    val startTime: String,
    val endTime: String,
    var isBooked: Boolean = false
)
