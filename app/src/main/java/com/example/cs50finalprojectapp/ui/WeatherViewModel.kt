package com.example.cs50finalprojectapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs50finalprojectapp.BuildConfig
import com.example.cs50finalprojectapp.network.NetworkResponse
import com.example.cs50finalprojectapp.network.RetrofitInstance
import com.example.cs50finalprojectapp.network.weathermodels.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class WeatherViewModel: ViewModel() {
    private val _weatherResponse: MutableStateFlow<NetworkResponse<WeatherModel>> = MutableStateFlow(NetworkResponse.Loading(""))
    val weatherResponse: StateFlow<NetworkResponse<WeatherModel>> = _weatherResponse.asStateFlow()
    fun getForecast(city: String) {
        viewModelScope.launch {
            _weatherResponse.value = NetworkResponse.Loading("Loading")
            try{
                val response = RetrofitInstance.weatherRetrofitService.getForecast(BuildConfig.WEATHER_API_KEY, city)
                if(response.code() != 200) {
                    _weatherResponse.value = NetworkResponse.Error("Unable to get data for ${city}, please try again")
                } else {
                    response.body()?.let {
                        _weatherResponse.value = NetworkResponse.Success(it)
                        Log.d("weatherResponse", _weatherResponse.value.toString())
                    }

                }
            } catch(e: IOException) {
                _weatherResponse.value = NetworkResponse.Error("Internet connection error")
            }


        }


    }
}