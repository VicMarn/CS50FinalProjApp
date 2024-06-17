package com.example.cs50finalprojectapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs50finalprojectapp.network.ActivityRecord
import com.example.cs50finalprojectapp.network.RetrofitInstance
import com.example.cs50finalprojectapp.network.Summary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FinalProjectViewModel: ViewModel() {

    var summary: Summary by mutableStateOf(Summary())
    private val _allRecords: MutableStateFlow<List<ActivityRecord>> = MutableStateFlow(listOf())
    val allRecords: StateFlow<List<ActivityRecord>> = _allRecords.asStateFlow()
    fun fetchSummary() {
        viewModelScope.launch {
             summary = RetrofitInstance.retrofitService.getSummary()
        }
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
            _allRecords.value = popListElement(_allRecords.value,id)
            Log.d("allRecordsDelete",_allRecords.value.toString())
        }
    }

    fun popListElement(list: List<ActivityRecord>, id: Int): List<ActivityRecord> {
        return list.filter { it -> it.id != id }
    }
}