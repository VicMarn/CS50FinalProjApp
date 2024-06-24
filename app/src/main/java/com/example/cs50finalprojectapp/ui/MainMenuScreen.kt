package com.example.cs50finalprojectapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cs50finalprojectapp.FinalProjectScreen
import com.example.cs50finalprojectapp.R
import com.example.cs50finalprojectapp.network.NetworkResponse
import com.example.cs50finalprojectapp.network.weathermodels.WeatherModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigationButtonClicked: (route: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        Text("Running records",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
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
        Row() {
            MainMenuCard(
                text = R.string.forecast,
                imageVector = Icons.Filled.Search,
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



