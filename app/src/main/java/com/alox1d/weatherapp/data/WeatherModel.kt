package com.alox1d.weatherapp.data

data class WeatherModel(
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val iconUrl: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String,
)
