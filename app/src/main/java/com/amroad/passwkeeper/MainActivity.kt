package com.amroad.passwkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amroad.passwkeeper.ui.AppRoot
import com.amroad.passwkeeper.ui.theme.PasswordKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordKeeperTheme {
                AppRoot()
            }
        }
    }
}
