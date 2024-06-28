package com.example.cs50finalprojectapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cs50finalprojectapp.R
import com.example.cs50finalprojectapp.network.NetworkResponse
import com.example.cs50finalprojectapp.network.weathermodels.WeatherModel

@Composable
fun ForecastScreen(
    weatherViewModel: WeatherViewModel,
) {
    val weatherResponse by weatherViewModel.weatherResponse.collectAsState()
    var cityInput by remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){

            OutlinedTextField(
                value = cityInput,
                onValueChange = {it: String -> cityInput = it},
                label = { Text("Insert city name") },
                placeholder = { Text("City name") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF843DDC), unfocusedBorderColor = Color(0xFF843DDC))
            )
            IconButton(onClick = {
                weatherViewModel.getForecast(cityInput)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "icon button to search for city",
                    tint = Color(0xFF843DDC))
            }

        }
        when(val weatherResponse = weatherResponse) {
            is NetworkResponse.Loading -> LoadingMessage(loadingMessage = weatherResponse.message)
            is NetworkResponse.Success -> WeatherDisplay(weatherData = weatherResponse.data)
            is NetworkResponse.Error -> ErrorMessage(errorMessage = weatherResponse.errorMessage){ weatherViewModel.getForecast(cityInput)}
        }
    }

}





@Composable
fun WeatherDisplay(weatherData: WeatherModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF843DDC))
            Text(
                "${weatherData.location.name}, ${weatherData.location.country}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF843DDC)
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(
                    weatherData.current.condition.icon
                        .replace("//","https://")
                        .replace("64x64","128x128"))
                .crossfade(true)
                .build(),
            modifier = Modifier.size(140.dp),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Current Weather Image"
        )
        Text(
            "${weatherData.current.temp_c} °C",
            fontSize = 40.sp,

            )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Max: ${weatherData.forecast.forecastday[0].day.maxtemp_c}°C",
                color = Color.Red,
                fontSize = 20.sp)
            Text(
                text = "Min: ${weatherData.forecast.forecastday[0].day.mintemp_c}°C",
                color = Color.Blue,
                fontSize = 20.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Humidity: ${weatherData.current.humidity}%",
                fontSize = 20.sp)
            Text(
                text = "Precipitation: ${weatherData.current.precip_mm}mm",
                fontSize = 20.sp)
        }
        Text("Hourly Forecast", fontSize = 20.sp, modifier = Modifier.padding(top = 24.dp, bottom = 16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(weatherData.forecast.forecastday[0].hour) {
                    item ->
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(item.time.split(" ")[1])
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https:${item.condition.icon}")
                            .crossfade(true)
                            .build(),
                        modifier = Modifier.size(60.dp),
                        contentDescription = "Hour forecast image")
                    Text("${item.temp_c}°C")
                    Text("${item.precip_mm}mm")
                }

            }
        }
    }
}