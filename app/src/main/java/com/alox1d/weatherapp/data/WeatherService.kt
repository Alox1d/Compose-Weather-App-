package com.alox1d.weatherapp.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.alox1d.weatherapp.API_KEY
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class WeatherService {
    fun getData(
        city: String,
        context: Context,
        daysList: MutableState<List<WeatherModel>>,
        currentDay: MutableState<WeatherModel>
    ) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
                "&q=$city" +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val list = getWeatherByDays(response)
                currentDay.value = list[0]
                daysList.value = list

                Log.d("MyLog", "Volley Response: $response")
            },
            { error ->
                Log.d("MyLog", "Volley Error: $error")
            }
        )
        queue.add(stringRequest)
    }

    private fun getWeatherByDays(response: String): List<WeatherModel> {
        if (response.isEmpty()) return emptyList() // Better than listOf, because uses same object EMPTY_LIST https://stackoverflow.com/questions/39400238/list-of-or-collections-emptylist
        val list = mutableListOf<WeatherModel>()
        val mainObject = JSONObject(response)

        val city = mainObject.getJSONObject("location").getString("name")

        val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        for (i in 0 until days.length()) {
            val forecastDay: JSONObject = days[i] as JSONObject
            list.add(
                WeatherModel(
                    city = city,
                    time = forecastDay.getString("date"),
                    currentTemp = "",
                    condition = forecastDay.getJSONObject("day").getJSONObject("condition")
                        .getString("text"),
                    iconUrl = forecastDay.getJSONObject("day").getJSONObject("condition")
                        .getString("icon"),
                    maxTemp = forecastDay.getJSONObject("day").getString("maxtemp_c"),
                    minTemp = forecastDay.getJSONObject("day").getString("mintemp_c"),
                    hours = forecastDay.getJSONArray("hour").toString()
                )
            )
        }
        list[0] = list[0].copy(
            time = mainObject.getJSONObject("current").getString("last_updated"),
            currentTemp = mainObject.getJSONObject("current").getString("temp_c")
        )
        return list
    }

    fun dummyData() = listOf(
        WeatherModel(
            city = "Ivanovo",
            time = "10:00",
            currentTemp = "-25°C",
            condition = "Sunny",
            iconUrl = "//cdn.weatherapi.com/weather/64x64/day/302.png",
            maxTemp = "",
            minTemp = "",
            hours = ""

        ),
        WeatherModel(
            city = "Ivanovo",
            time = "15.01.23",
            currentTemp = "",
            condition = "Sunny",
            iconUrl = "//cdn.weatherapi.com/weather/64x64/day/302.png",
            maxTemp = "-15°",
            minTemp = "-25°",
            hours = "Weather info for hours"
        ),
    )
}