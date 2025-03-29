package com.example.myai_assistant.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavigationBar(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination == Screen.MAIN.name,
            onClick = {
                navController.navigate(Screen.MAIN.name){
                    popUpTo(Screen.MAIN.name){inclusive = true}
                    launchSingleTop = true
                }
            },
            label = { Text("Home") },
            icon = { Icon((Icons.Default.Home), contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = currentDestination == Screen.BOOKMARK.name,
            onClick = {
                navController.navigate(Screen.BOOKMARK.name){
                    popUpTo(Screen.MAIN.name){inclusive = false}
                    launchSingleTop = true
                }
            },
            label = { Text("BookMark") },
            icon = { Icon((Icons.Default.Star), contentDescription = "BookMark") }
        )
    }
}