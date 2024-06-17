package com.example.cs50finalprojectapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cs50finalprojectapp.ui.AllRecordsScreen
import com.example.cs50finalprojectapp.ui.FinalProjectViewModel
import com.example.cs50finalprojectapp.ui.MainMenuScreen
import com.example.cs50finalprojectapp.ui.SummaryScreen


enum class FinalProjectScreen(@StringRes val title: Int) {
    MainMenu(R.string.main_menu),
    FullList(R.string.full_list),
    Summary(R.string.summary)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalProjectAppBar(
    canNavigateBack: Boolean,
    currentScreen: FinalProjectScreen,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = {navigateUp()}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun FinalProjectApp(
    navController:NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FinalProjectScreen.valueOf(
        backStackEntry?.destination?.route ?: FinalProjectScreen.MainMenu.name
    )
    Scaffold(
        topBar = {
            FinalProjectAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = FinalProjectScreen.MainMenu.name,
            modifier = Modifier.padding(it)
        ) {
            val viewModel: FinalProjectViewModel = FinalProjectViewModel()
            composable(route = FinalProjectScreen.MainMenu.name) {
                MainMenuScreen(
                    onNavigationButtonClicked = {route -> navController.navigate(route)}
                )
            }
            composable(route = FinalProjectScreen.FullList.name) {
                AllRecordsScreen(viewModel)
            }
            composable(route = FinalProjectScreen.Summary.name) {
                SummaryScreen(viewModel)
            }
        }
    }
}