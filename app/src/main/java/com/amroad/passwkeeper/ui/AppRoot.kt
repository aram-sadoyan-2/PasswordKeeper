package com.amroad.passwkeeper.ui

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.amroad.passwkeeper.helper.Routes
import com.amroad.passwkeeper.factory.PasscodeVmFactory
import com.amroad.passwkeeper.ui.home.HomeScreen
import com.amroad.passwkeeper.ui.passcodetype.PasscodeConfirmScreen
import com.amroad.passwkeeper.ui.passcodetype.PasscodeSetupScreen
import com.amroad.passwkeeper.ui.passcodetype.PasscodeUnlockScreen
import com.amroad.passwkeeper.viewmodel.ConfirmResult
import com.amroad.passwkeeper.viewmodel.PasscodeViewModel

@Composable
fun AppRoot() {
    val context = LocalContext.current
    val vm: PasscodeViewModel = viewModel(factory = PasscodeVmFactory(context))

    val nav = rememberNavController()

    // Decide start destination once
    val start = remember { if (vm.isPasscodeEnabled()) Routes.Unlock else Routes.Setup }

    NavHost(navController = nav, startDestination = start) {

        composable(Routes.Setup) {
            PasscodeSetupScreen(
                vm = vm,
                onCancel = {
                    vm.cancelSetup()
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Setup) { inclusive = true }
                    }
                },
                onNext = {
                    if (vm.onFirstEntryComplete()) {
                        nav.navigate(Routes.Confirm)
                    }
                }
            )
        }

        composable(Routes.Confirm) {
            PasscodeConfirmScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onConfirmed = {
                    val result = vm.onConfirmComplete()
                    if (result is ConfirmResult.Success) {
                        nav.navigate(Routes.Recovery) {
                            popUpTo(Routes.Setup) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.Recovery) {
            RecoveryQuestionScreen(
                onLater = {
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Recovery) { inclusive = true }
                    }
                },
                onDone = {
                    vm.markRecoveryQuestionSet()
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Recovery) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Unlock) {
            PasscodeUnlockScreen(
                vm = vm,
                onUnlocked = {
                    vm.unlockAsync { ok ->
                        if (ok) {
                            val next = if (vm.isRecoveryQuestionSet()) Routes.Home else Routes.Recovery
                            nav.navigate(next) {
                                popUpTo(Routes.Unlock) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        composable(Routes.Home) {
            HomeScreen()
        }
    }
}