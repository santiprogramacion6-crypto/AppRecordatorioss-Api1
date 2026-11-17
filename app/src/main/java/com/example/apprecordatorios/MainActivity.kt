package com.example.apprecordatorios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apprecordatorios.navigation.RecordatoriosApp
import com.example.apprecordatorios.ui.theme.AppRecordatoriosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRecordatoriosTheme {
                RecordatoriosApp()
            }
        }
    }
}