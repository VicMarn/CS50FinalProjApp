package com.example.cs50finalprojectapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs50finalprojectapp.FinalProjectScreen
import com.example.cs50finalprojectapp.R


@Composable
fun MainMenuScreen(
    onNavigationButtonClicked: (route: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
            ){
            Image(painterResource(
                id = R.drawable.running_tracker_transparent),
                contentDescription = "Running Tracker logo"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){Text(
            text="Running Tracker",
            color = Color(0xFF843DDC),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold)}
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainMenuCard(
                text = R.string.card_full_list_text,
                imageVector = ImageVector.vectorResource(id = R.drawable.menu_icon_listall),
                imageDescription = "List Icon",
                onClick = { onNavigationButtonClicked(FinalProjectScreen.FullList.name) },
                modifier.weight(1f)
            )
            MainMenuCard(
                text = R.string.summary,
                imageVector = ImageVector.vectorResource(id = R.drawable.menu_icon_summary),
                imageDescription = "Summary icon",
                onClick = { onNavigationButtonClicked(FinalProjectScreen.Summary.name) },
                modifier.weight(1f)
            )
        }
        Row() {
            MainMenuCard(
                text = R.string.forecast,
                imageVector = ImageVector.vectorResource(id = R.drawable.menu_icon_weather),
                imageDescription = "Weather forecast icon",
                onClick = { onNavigationButtonClicked(FinalProjectScreen.Forecast.name) },
            )
        }
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
        border = BorderStroke(1.dp,Color(0xFF843DDC)),
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



