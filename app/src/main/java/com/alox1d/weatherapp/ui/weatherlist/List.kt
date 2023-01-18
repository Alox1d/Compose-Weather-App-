package com.alox1d.weatherapp.ui.weatherlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.alox1d.weatherapp.data.WeatherModel
import com.alox1d.weatherapp.ui.screens.ListItem

@Composable
fun WeatherList(daysList: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            daysList
            //dummyData()
        ) { _, model ->
            ListItem(model, currentDay)
        }
    }
}