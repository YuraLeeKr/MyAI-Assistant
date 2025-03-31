package com.example.MyAIassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.MyAIassistant.screen.main.MainLayout
import com.example.MyAIassistant.ui.theme.MyAIassistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAIassistantTheme {
                MainLayout()
            }
        }
    }
}
