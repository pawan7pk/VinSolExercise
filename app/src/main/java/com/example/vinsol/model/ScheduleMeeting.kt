package com.example.vinsol.model


data class ScheduleMeeting(
    val start_time: String,
    val end_time: String,
    val description: String,
    val participants: List<String>
)
