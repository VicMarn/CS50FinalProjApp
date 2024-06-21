package com.example.cs50finalprojectapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.example.cs50finalprojectapp.ui.theme.CS50FinalProjectAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CS50FinalProjectAppTheme {
                FinalProjectApp()
            }
        }
    }
}