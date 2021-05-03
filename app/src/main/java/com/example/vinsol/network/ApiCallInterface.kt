package com.example.vinsol.network

import com.example.vinsol.model.ScheduleMeeting
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallInterface {

    @GET("/api/schedule")
    suspend fun getScheduleMeeting(@Query("date") date: String): Response<ArrayList<ScheduleMeeting>>
}