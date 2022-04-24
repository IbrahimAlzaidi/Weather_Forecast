package com.example.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {


    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repo.getWeather(cityQuery = city,units = units)
    }



}