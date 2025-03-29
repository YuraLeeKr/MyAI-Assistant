package com.example.myai_assistant.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainLayout() {
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ){ innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}