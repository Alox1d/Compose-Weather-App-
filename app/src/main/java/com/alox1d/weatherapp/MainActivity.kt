package com.alox1d.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alox1d.weatherapp.data.WeatherModel
import com.alox1d.weatherapp.data.WeatherService
import com.alox1d.weatherapp.ui.screens.MainCard
import com.alox1d.weatherapp.ui.screens.TabLayout
import com.alox1d.weatherapp.ui.theme.WeatherAppTheme
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

const val API_KEY = "5a3c6eb53dd240f3b77221117230801"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                //States
                val (daysList, currentDay) = initStates()

                //Data
                initData("Ivanovo", this, daysList, currentDay)

                //UI
                Background()
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }

    @Composable
    private fun initStates(): Pair<MutableState<List<WeatherModel>>, MutableState<WeatherModel>> {
        val daysList = remember {
            mutableStateOf(listOf<WeatherModel>())
        }
        val currentDay = remember {
            mutableStateOf(
                WeatherModel(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                )
            )
        }
        return Pair(daysList, currentDay)
    }

    private fun initData(
        city: String,
        context: Context,
        daysList: MutableState<List<WeatherModel>>,
        currentDay: MutableState<WeatherModel>
    ) {
        WeatherService().getData(city, context, daysList, currentDay)
    }

    @Composable
    private fun Background() {
        Image(
            painter = painterResource(id = R.drawable.weather), contentDescription = "im",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.FillBounds
        )
    }
}




// OLD Weather App
//@Composable
//fun Greeting(name: String, context: Context) {
//    val state = remember {
//        mutableStateOf("Unknown")
//    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxHeight(0.5f)
//                .fillMaxWidth(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "Temp in $name = ${state.value} C")
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Button(
//                onClick = {
//                    getResult(name, state, context)
//                }, modifier = Modifier
//                    .padding(5.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(text = "Refresh")
//            }
//        }
//    }
//}
//
//private fun getResult(city: String, state: MutableState<String>, context: Context) {
//    val url = "https://api.weatherapi.com/v1/current.json" +
//            "?key=$API_KEY&" +
//            "q=$city" +
//            "&aqi=no"
//    val queue = Volley.newRequestQueue(context)
//    val stringRequest = StringRequest(
//        Request.Method.GET,
//        url,
//        { response ->
//            val obj = JSONObject(response)
//            state.value = obj.getJSONObject("current").getString("temp_c")
//        },
//        { error ->
//        }
//    )
//    queue.add(stringRequest)
//}