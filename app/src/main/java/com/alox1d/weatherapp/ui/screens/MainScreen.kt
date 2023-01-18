package com.alox1d.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.alox1d.weatherapp.R
import com.alox1d.weatherapp.data.WeatherModel
import com.alox1d.weatherapp.data.WeatherModelMapper
import com.alox1d.weatherapp.data.WeatherService
import com.alox1d.weatherapp.ui.weatherlist.WeatherList
import com.alox1d.weatherapp.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.roundToInt

@Composable
fun MainCard(currentDay: MutableState<WeatherModel>) {
    val temp = currentDay.value.currentTemp.toFloatOrNull()
    val maxTemp = currentDay.value.maxTemp.toFloatOrNull()
    val minTemp = currentDay.value.minTemp.toFloatOrNull()

    Column(
        modifier = Modifier
            .padding(5.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = currentDay.value.time, // "01 Jan 2023 13:00"
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 8.dp),
                        model = "https:" + currentDay.value.iconUrl, // "https://cdn.weatherapi.com/weather/64x64/night/116.png"
                        contentDescription = "im2",
                    )
                }
                Text(
                    text = currentDay.value.city, // "Ivanovo"
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = if (temp != null) temp.roundToInt().toString() + "°C" // "23°C", else will be string "null"
                    else "${maxTemp?.roundToInt()}°C/" +
                            "${minTemp?.roundToInt()}°C",
                style = TextStyle(fontSize = 65.sp),
                color = Color.White
                )
                Text(
                    text = currentDay.value.condition, // "Sunny"
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_24),
                            contentDescription = "im3",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${maxTemp?.roundToInt()}°C/" +
                                "${minTemp?.roundToInt()}°C", //"23°C/12°C"
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cloud_sync_24),
                            contentDescription = "im4",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope() // для анимации

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp)) // скруглённые края
            .padding(5.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }

                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when (index) {
                0 -> WeatherModelMapper().mapHoursStringToDomain(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }
            WeatherList(daysList = list, currentDay = currentDay)
        }
    }
}

