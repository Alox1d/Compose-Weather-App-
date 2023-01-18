package com.alox1d.weatherapp.data

import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt

class WeatherModelMapper {
    fun mapHoursStringToDomain(hours: String): List<WeatherModel> {
        if (hours.isEmpty()) return emptyList()
        val hoursArray = JSONArray(hours)
        val list = mutableListOf<WeatherModel>()
        for (i in 0 until hoursArray.length()) {
            val item = hoursArray[i] as JSONObject
            list.add(
                WeatherModel(
                    city = "",
                    time = item.getString("time"),
                    currentTemp = item.getString("temp_c").toFloat().roundToInt().toString() + "°C",
                    condition = item.getJSONObject("condition").getString("text"),
                    iconUrl = item.getJSONObject("condition").getString("icon"),
                    "",
                    "",
                    ""
                )
            )
        }
        return list
    }
}