package com.example.cs50finalprojectapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cs50finalprojectapp.FinalProjectScreen
import com.example.cs50finalprojectapp.R


@Composable
fun MainMenuScreen(
    onNavigationButtonClicked: (route: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onNavigationButtonClicked(FinalProjectScreen.FullList.name) }
        ) {
            Text(text = stringResource(id = R.string.full_list))
        }
        Button(
            onClick = {onNavigationButtonClicked(FinalProjectScreen.Summary.name)}
        ) {
            Text(text = stringResource(id = R.string.summary))
        }
    }
}