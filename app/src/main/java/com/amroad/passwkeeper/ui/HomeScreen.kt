package com.amroad.passwkeeper.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.ui.screen.generator.GeneratorScreen
import com.amroad.passwkeeper.ui.screen.home.HomeViewModel
import com.amroad.passwkeeper.ui.tab.HomeBottomPill
import com.amroad.passwkeeper.ui.tab.HomeTab
import com.amroad.passwkeeper.ui.tab.tabscreen.SettingsTabScreen
import com.amroad.passwkeeper.ui.tab.tabscreen.VaultScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    var tab by remember { mutableStateOf(HomeTab.VAULT) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            when (tab) {
                HomeTab.VAULT -> VaultScreen()
                HomeTab.GENERATOR -> GeneratorScreen()
                HomeTab.SETTINGS -> SettingsTabScreen(
                    requirePassOnLaunch = true,                 // TODO connect to prefs
                    onRequirePassOnLaunchChange = { /* TODO */ }
                )
            }
        }

        // Bottom pill
        HomeBottomPill(
            selected = tab,
            onSelect = { tab = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        )
    }
}




