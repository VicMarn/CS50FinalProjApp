package com.example.cs50finalprojectapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs50finalprojectapp.network.ActivityRecord

@Composable
fun AllRecordsScreen(
    viewModel: FinalProjectViewModel
) {
    LaunchedEffect(2) {
        viewModel.fetchAllRecords()
    }
    RecordsLazyColumn(viewModel)
}

@Composable
fun RecordsLazyColumn(
    viewModel: FinalProjectViewModel
) {
    val allRecords by viewModel.allRecords.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(allRecords) { record ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFF3DDC84))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "${record.id}")
                    Text(text = "Date: ${record.date}")
                    Text(text = "Distance: ${record.distance}")
                    Text(text = "Time: ${record.time}")
                    Text(text = "Comment: ${record.comment}")
                    Button(onClick = {
                        viewModel.deleteRecord(record.id)
                    }){
                        Text(text = "Delete")
                    }
                }

            }
        }
    }
}