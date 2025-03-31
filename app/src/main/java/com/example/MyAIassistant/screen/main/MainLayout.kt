package com.example.MyAIassistant.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.MyAIassistant.Navigation.AppNavGraph
import com.example.MyAIassistant.screen.components.BottomNavigationBar

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