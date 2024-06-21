package com.example.cs50finalprojectapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs50finalprojectapp.network.ActivityRecord
import com.example.cs50finalprojectapp.network.PostRecord
import com.example.cs50finalprojectapp.network.RetrofitInstance
import com.example.cs50finalprojectapp.network.Summary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FinalProjectViewModel: ViewModel() {

    private val _allRecords: MutableStateFlow<List<ActivityRecord>> = MutableStateFlow(listOf())
    val allRecords: StateFlow<List<ActivityRecord>> = _allRecords.asStateFlow()
    private val _summary: MutableStateFlow<Summary> = MutableStateFlow(Summary())
    val summary: StateFlow<Summary> = _summary.asStateFlow()

    private val _formUiState: MutableStateFlow<RecordFormUiState> = MutableStateFlow(RecordFormUiState())
    val formUiState:  StateFlow<RecordFormUiState> = _formUiState.asStateFlow()

    fun updateOpenPostDialog(value: Boolean) {
        _formUiState.update {currentState ->
            currentState.copy(
                openPostDialog = value
            )
        }
    }

    fun updateHours(value: String) {
        if(((value.length <= 2) and (value.toIntOrNull() != null)) or (value.isEmpty()))
        {
            _formUiState.update { currentState ->
                currentState.copy(
                    hours = value
                )
            }
        }
    }

    fun updateMinutes(value: String) {
        if(((value.length <= 2) and (value.toIntOrNull() != null)) or (value.isEmpty()))
        {
            _formUiState.update { currentState ->
                currentState.copy(
                    minutes = value
                )
            }
        }

    }
    fun updateSeconds(value: String) {
        if(((value.length <= 2) and (value.toIntOrNull() != null)) or (value.isEmpty()))
        {
            _formUiState.update { currentState ->
                currentState.copy(
                    seconds = value
                )
            }
        }
    }

    fun updateComment(value: String){
        _formUiState.update { currentState ->
            currentState.copy(
                comment = value
            )
        }
    }

    fun updateDistance(value: String) {
        var inputValue = value
        if ((inputValue.toFloatOrNull() != null) or (inputValue.isEmpty())) {
            updateDistanceForDisplay(inputValue)
            if(value.isEmpty()) {
                inputValue = "0.0"
            }
            _formUiState.update { currentState ->
                currentState.copy(
                    distance = inputValue.toFloat()
                )
            }
        }

    }

    private fun updateDistanceForDisplay(value: String) {
        _formUiState.update { currentState ->
            currentState.copy(
                distanceForDisplay = value
            )
        }
    }

    fun updateDate(value: String) {
        val dateList = value.split("-")
        val newValue = "${dateList[2]}-${dateList[1]}-${dateList[0]}"
        _formUiState.update { currentState ->
            currentState.copy(
                date = newValue
            )
        }
    }

    fun updateDateForDisplay(value: String){
        _formUiState.update { currentState ->
            currentState.copy(
                dateForDisplay = value
            )
        }
    }

    fun resetFormUiState(){
        _formUiState.value = RecordFormUiState()
    }

    fun fetchSummary() {
        viewModelScope.launch {
             _summary.value = RetrofitInstance.retrofitService.getSummary()
        }
    }

    init {
        fetchAllRecords()
        fetchSummary()
        Log.d("initTrigger", "init is running")
    }

    fun fetchAllRecords() {
        viewModelScope.launch {
            _allRecords.value = RetrofitInstance.retrofitService.getAllRecords()
            Log.d("allRecords",_allRecords.value.toString())
        }
    }

    fun deleteRecord(id: Int) {
        viewModelScope.launch {
            RetrofitInstance.retrofitService.deleteRecord(id)
            fetchAllRecords()
            //_allRecords.value = popListElement(_allRecords.value,id)
            Log.d("allRecordsDelete",_allRecords.value.toString())
        }
    }

    fun createRecord() {
        val record = PostRecord(
            date= formUiState.value.date,
            distance = formUiState.value.distance,
            time = "${formUiState.value.hours}:${formUiState.value.minutes}:${formUiState.value.seconds}",
            comment = formUiState.value.comment)
        viewModelScope.launch {
            RetrofitInstance.retrofitService.postRecord(record)
            fetchAllRecords()
        }
    }

    /*fun popListElement(list: List<ActivityRecord>, id: Int): List<ActivityRecord> {
        return list.filter { it -> it.id != id }
    }*/


}