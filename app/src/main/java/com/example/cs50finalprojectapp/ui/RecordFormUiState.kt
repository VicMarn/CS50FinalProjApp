package com.example.cs50finalprojectapp.ui

data class RecordFormUiState(
    val openPostDialog: Boolean = false,
    val distance: Float = 0.0f,
    /* distanceForDisplay is only used to display a string with the current
       input value for distance in the record creating form */
    val distanceForDisplay: String = "",
    val date: String = "",
    /* dateForDisplay is only used to display a string with the current
       input value for date, using the format dd-MM-yyyy, in the record creating form */
    val dateForDisplay: String = "",
    val hours: String = "",
    val minutes: String = "",
    val seconds: String = "",
    val comment: String = ""
)
