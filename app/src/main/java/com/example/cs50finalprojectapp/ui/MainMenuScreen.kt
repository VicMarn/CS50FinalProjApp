package com.example.cs50finalprojectapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cs50finalprojectapp.FinalProjectScreen
import com.example.cs50finalprojectapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigationButtonClicked: (route: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().border(BorderStroke(1.dp,Color.Red)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainMenuCard(
            text = R.string.card_full_list_text,
            imageVector = Icons.Filled.List,
            imageDescription = "List Icon",
            onClick = { onNavigationButtonClicked(FinalProjectScreen.FullList.name) },
            modifier.weight(1f)
        )
        MainMenuCard(
            text = R.string.summary,
            imageVector = Icons.Filled.Info,
            imageDescription = "Summary icon",
            onClick = { onNavigationButtonClicked(FinalProjectScreen.Summary.name) },
            modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuCard(
    @StringRes text: Int,
    imageVector: ImageVector,
    imageDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {onClick()},
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp,Color(0xFF3DDC84)),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = imageVector,
                contentDescription = imageDescription,
                tint = Color(0xFF3DDC84))
            Text(text= stringResource(id = text), color= Color(0xFF3DDC84))
        }
    }
}