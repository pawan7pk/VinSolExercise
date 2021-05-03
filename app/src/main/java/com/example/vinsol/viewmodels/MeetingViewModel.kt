package com.example.vinsol.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinsol.model.ScheduleMeeting
import com.example.vinsol.network.RetrofitNetworking
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeetingViewModel : ViewModel() {

    val scheduleMeetingList = MutableLiveData<ArrayList<ScheduleMeeting>>()

    fun callScheduleMeetingAPI(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitNetworking.create().getScheduleMeeting(date)

            withContext(Dispatchers.Main) {
                when {
                    response.isSuccessful && response.body() != null -> {
                        Log.i("log", "Response: ${Gson().toJson(response.body())}")
                        scheduleMeetingList.postValue(response.body())
                    }
                    else -> {
                        Log.e("log", "Response Error")
                    }
                }
            }
        }
    }
}