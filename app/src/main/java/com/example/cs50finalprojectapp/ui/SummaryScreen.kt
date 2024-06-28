package com.example.cs50finalprojectapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs50finalprojectapp.network.NetworkResponse
import com.example.cs50finalprojectapp.network.Summary
import java.util.Locale

@Composable
fun SummaryScreen(
    viewModel: FinalProjectViewModel
) {
    val summary: NetworkResponse<Summary> by viewModel.summary.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchSummary()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(val summary = summary){
            is NetworkResponse.Loading -> LoadingMessage(loadingMessage = summary.message)
            is NetworkResponse.Success -> SummaryCard(summary = summary.data)
            is NetworkResponse.Error -> ErrorMessage(summary.errorMessage) { viewModel.fetchSummary() }
        }

    }
}



@Composable
fun SummaryCard(summary: Summary) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFF843DDC))
    ) {
        val formattedTotalTime: Time = formatTime(summary.totalTime)
        val averageTime = if(summary.numberOfDays != 0){
            formatTime(totalTime = summary.totalTime / summary.numberOfDays)
        } else {
            Time()
        }
        val averageDistance: String = String.format(
            Locale.ENGLISH,
            "%.2f",
            summary.totalDistance / summary.numberOfDays
        )
        val fontSize = 20.sp
        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                Text(text = "Number of records: ", fontWeight = FontWeight.Bold, fontSize = fontSize, color= Color(0xFF843DDC))
                Text(text = "${summary.numberOfDays} records", fontSize = fontSize)
            }
            Row() {
                Text(text = "Total distance: ", fontWeight = FontWeight.Bold, fontSize = fontSize, color= Color(0xFF843DDC))
                Text(text = "${summary.totalDistance} km", fontSize = fontSize)
            }
            Row() {
                Text(text = "Average distance: ", fontWeight = FontWeight.Bold, fontSize = fontSize, color= Color(0xFF843DDC))
                Text(text = "${averageDistance} km", fontSize = fontSize)
            }
            Row() {
                Text(text = "Total time: ", fontWeight = FontWeight.Bold, fontSize = fontSize, color= Color(0xFF843DDC))
                Text(
                    text = "${formattedTotalTime.hours}h ${formattedTotalTime.minutes}min ${formattedTotalTime.seconds}s",
                    fontSize = fontSize)
            }
            Row() {
                Text(text = "Average time: ", fontWeight = FontWeight.Bold, fontSize = fontSize, color= Color(0xFF843DDC))
                Text(text = "${averageTime.hours}h ${averageTime.minutes}min ${averageTime.seconds}s", fontSize = fontSize)
            }
        }

    }
}

data class Time(
    var hours: Int = 0,
    var minutes: Int = 0,
    var seconds: Int = 0
)
fun formatTime(
    totalTime: Int
): Time {
    val hours: Int = totalTime / 3600
    val minutes: Int = (totalTime - (3600 * hours)) / 60
    val seconds: Int = totalTime - (3600 * hours) - (60 * minutes)
    return Time(
        hours = hours,
        minutes = minutes,
        seconds = seconds
    )
}