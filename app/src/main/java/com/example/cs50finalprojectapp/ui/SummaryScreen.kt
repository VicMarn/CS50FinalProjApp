package com.example.cs50finalprojectapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
        modifier = Modifier.fillMaxWidth(),
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
        border = BorderStroke(1.dp, Color(0xFF3DDC84))
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Number of days: ${summary.numberOfDays} days")
            Text(text = "Total distance: ${summary.totalDistance} km")
            Text(text = "Average distance: ${averageDistance} km")
            Text(text = "Total time: ${formattedTotalTime.hours}h ${formattedTotalTime.minutes}min ${formattedTotalTime.seconds}s")
            Text(text = "Average time: ${averageTime.hours}h ${averageTime.minutes}min ${averageTime.seconds}s")
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