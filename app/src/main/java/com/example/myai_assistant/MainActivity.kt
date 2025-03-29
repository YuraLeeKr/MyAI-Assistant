package com.example.myai_assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myai_assistant.screen.MainLayout
import com.example.myai_assistant.ui.theme.MyAI_assistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAI_assistantTheme {
                MainLayout()
            }
        }
    }
}
