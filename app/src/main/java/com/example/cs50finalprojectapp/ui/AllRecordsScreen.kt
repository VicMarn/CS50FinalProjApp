package com.example.cs50finalprojectapp.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs50finalprojectapp.network.ActivityRecord
import com.example.cs50finalprojectapp.network.NetworkResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AllRecordsScreen(
    viewModel: FinalProjectViewModel = viewModel()
) {
    val allRecords by viewModel.allRecords.collectAsState()
    val formUiState by viewModel.formUiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchAllRecords()
    }

    when {
        formUiState.openPostDialog -> PostDialog(
            onDismissRequest = {
                viewModel.updateOpenPostDialog(false)
                viewModel.resetFormUiState()
            },
            viewModel = viewModel,
            formUiState
        )
    }
    when(val allRecords = allRecords) {
        is NetworkResponse.Loading -> LoadingMessage(loadingMessage = allRecords.message)
        is NetworkResponse.Success -> Column {
            PostRecordRow(viewModel)
            RecordsLazyColumn(viewModel, allRecords.data)
        }
        is NetworkResponse.Error -> ErrorMessage(errorMessage = allRecords.errorMessage) {
            viewModel.fetchAllRecords()
        }
    }
}

@Composable
fun RecordsLazyColumn(
    viewModel: FinalProjectViewModel,
    allRecords: List<ActivityRecord>
) {
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
                    val openDeleteDialog = remember { mutableStateOf(false) }
                    Text(text = "${record.id}")
                    Text(text = "Date: ${record.date}")
                    Text(text = "Distance: ${record.distance}km")
                    Text(text = "Time: ${record.time}")
                    Text(text = "Comment: ${record.comment}")
                    Button(onClick = {
                        openDeleteDialog.value = true
                    }) {
                        Text(text = "Delete")
                    }
                    val context = LocalContext.current
                    when {
                        openDeleteDialog.value -> {
                            DeleteDialog(
                                onDismissRequest = { openDeleteDialog.value = false },
                                onConfirmRequest = {
                                    viewModel.deleteRecord(record.id, context)
                                    openDeleteDialog.value = false

                                }
                            )
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun DeleteDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmRequest() }
            )
            {
                Text("Delete")
            }

        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Cancel")
            }

        },
        icon = { Icons.Filled.Delete },
        title = { Text(text = "Delete Record") },
        text = { Text(text = "Are you sure you want to delete this record?") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDialog(
    onDismissRequest: () -> Unit,
    viewModel: FinalProjectViewModel,
    formUiState: RecordFormUiState
) {
    val openCalendarDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    var isDateInvalid by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }

    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp,Color.Black)
        ) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(1F)
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    openCalendarDialog.value -> DatePickerDialog(
                        onDismissRequest = { openCalendarDialog.value = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openCalendarDialog.value = false
                                    viewModel.updateDate(
                                        datePickerState.selectedDateMillis?.convertMillisToDate()
                                            ?: ""
                                    )
                                    viewModel.updateDateForDisplay(
                                        datePickerState.selectedDateMillis?.convertMillisToDate()
                                            ?: ""
                                    )
                                    isDateInvalid = false
                                }
                            ) {
                                Text("Confirm")
                            }
                        },
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        dismissButton = {
                            TextButton(
                                onClick = { openCalendarDialog.value = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
                Text("Enter new record!", fontWeight = FontWeight.Bold)
                Text("Selected date: ${formUiState.dateForDisplay}", modifier = Modifier.padding(16.dp))
                Button(onClick = {
                    openCalendarDialog.value = true
                }) {
                    Text("Pick a date")
                }
                if (isDateInvalid) {
                    Text("Please enter a valid date", color = MaterialTheme.colorScheme.error)
                }
                Divider()
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                    Text("Time ", fontWeight = FontWeight.Bold)
                    TextField(
                        value = formUiState.hours,
                        onValueChange = { it ->
                            if (it.isEmpty() or (it.toIntOrNull() in 0..23)) {
                                viewModel.updateHours(it)
                            }
                        },
                        modifier = Modifier.width(60.dp),
                        placeholder = { Text(text = "00") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                    )
                    Text(":", fontWeight = FontWeight.Bold)
                    TextField(
                        value = formUiState.minutes,
                        onValueChange = { it ->
                            if (it.isEmpty() or (it.toIntOrNull() in 0..59))
                                viewModel.updateMinutes(it)
                        },
                        modifier = Modifier.width(60.dp),
                        placeholder = { Text(text = "00") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                    )
                    Text(":", fontWeight = FontWeight.Bold)
                    TextField(
                        value = formUiState.seconds,
                        onValueChange = { it ->
                            if (it.isEmpty() or (it.toIntOrNull() in 0..59)) {
                                viewModel.updateSeconds(it)
                            }
                        },
                        modifier = Modifier.width(60.dp),
                        placeholder = { Text(text = "00") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                    )
                }
                Divider()
                TextField(
                    value = formUiState.distanceForDisplay,
                    onValueChange = { viewModel.updateDistance(it) },
                    modifier = Modifier.padding(vertical = 16.dp),
                    label = { Text("Distance") },
                    placeholder = { Text("0.0") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                )
                Divider()
                TextField(
                    value = formUiState.comment,
                    onValueChange = { viewModel.updateComment(it) },
                    modifier = Modifier.padding( vertical = 16.dp),
                    label = { Text(text = "Comment") },
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                )
                Divider()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel", color = MaterialTheme.colorScheme.error)
                    }
                    val context = LocalContext.current
                    TextButton(onClick = {
                        when {
                            dateValidation(formUiState.date) -> isDateInvalid = true
                            else -> {
                                viewModel.createRecord(context = context)
                                viewModel.updateOpenPostDialog(false)
                                viewModel.resetFormUiState()
                            }
                        }

                    }) {
                        Text(text = "Create")
                    }
                }

            }
        }

    }
}

@Composable
fun PostRecordRow(viewModel: FinalProjectViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.updateOpenPostDialog(true) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        Text(text = "Add new record")
    }
}

fun dateValidation(date: String): Boolean {
    return date.isEmpty()
}

fun Long.convertMillisToDate(): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@convertMillisToDate
        val zoneOffset = get(Calendar.ZONE_OFFSET)
        val dstOffset = get(Calendar.DST_OFFSET)
        add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    }
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    return sdf.format(calendar.time)
}

