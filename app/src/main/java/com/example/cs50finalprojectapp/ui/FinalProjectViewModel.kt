package com.example.cs50finalprojectapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs50finalprojectapp.network.ActivityRecord
import com.example.cs50finalprojectapp.network.NetworkResponse
import com.example.cs50finalprojectapp.network.PostRecord
import com.example.cs50finalprojectapp.network.RetrofitInstance
import com.example.cs50finalprojectapp.network.Summary
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

class FinalProjectViewModel: ViewModel() {

    private val _allRecords: MutableStateFlow<NetworkResponse<List<ActivityRecord>>> = MutableStateFlow(NetworkResponse.Success(listOf()))
    val allRecords: StateFlow<NetworkResponse<List<ActivityRecord>>> = _allRecords.asStateFlow()
    private val _summary: MutableStateFlow<NetworkResponse<Summary>> = MutableStateFlow(NetworkResponse.Success(Summary()))
    val summary: StateFlow<NetworkResponse<Summary>> = _summary.asStateFlow()
    private val _formUiState: MutableStateFlow<RecordFormUiState> = MutableStateFlow(RecordFormUiState())
    val formUiState:  StateFlow<RecordFormUiState> = _formUiState.asStateFlow()

    init {
        Log.d("initTrigger", "init is running")
    }

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
        _summary.value = NetworkResponse.Loading("Loading")
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.retrofitService.getSummary()
                if(response.isSuccessful) {
                    response.body()?.let { summary ->
                        _summary.value = NetworkResponse.Success(summary)
                    }
                } else {
                    _summary.value = NetworkResponse.Error("Something went wrong: code ${response.code()}")
                }
            } catch(e: IOException) {
                _summary.value = NetworkResponse.Error("Connection error: ${e.message}")
            } catch(e: HttpException) {
                _summary.value = NetworkResponse.Error("Http exception: ${e.message}")
            }

        }
    }

    fun fetchAllRecords() {
        _allRecords.value = NetworkResponse.Loading("Loading...")
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.retrofitService.getAllRecords()
                if(response.isSuccessful) {
                    response.body()?.let { allRecords ->
                        _allRecords.value = NetworkResponse.Success(allRecords)
                    }
                } else {
                    _allRecords.value = NetworkResponse.Error("Something went wrong: code ${response.code()}")
                }
            } catch(e: IOException) {
                _allRecords.value = NetworkResponse.Error("Connection error: ${e.message}")
            } catch(e: HttpException) {
                _allRecords.value = NetworkResponse.Error("Http exception: ${e.message}")
            }
        }

    }

    fun deleteRecord(id: Int, context: Context) {
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.retrofitService.deleteRecord(id)
                if(response.isSuccessful){
                    fetchAllRecords()
                    Toast.makeText(context,"Deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"An error occurred: code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch(e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                e.printStackTrace()
                Toast.makeText(context, "Http Exception: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }


    }


    fun createRecord(context: Context) {
        val record = PostRecord(
            date= formUiState.value.date,
            distance = formUiState.value.distance,
            time = "${formUiState.value.hours}:${formUiState.value.minutes}:${formUiState.value.seconds}",
            comment = formUiState.value.comment)
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.retrofitService.postRecord(record)
                if(response.isSuccessful) {
                    fetchAllRecords()
                    Toast.makeText(context,"Record created successfully",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"An error occurred: code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch(e: IOException) {
                e.printStackTrace()
                Toast.makeText(context,"Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                e.printStackTrace()
                Toast.makeText(context, "Http Exception: ${e.message}", Toast.LENGTH_SHORT).show()
            }


        }
    }

    /*fun popListElement(list: List<ActivityRecord>, id: Int): List<ActivityRecord> {
        return list.filter { it -> it.id != id }
    }*/


}