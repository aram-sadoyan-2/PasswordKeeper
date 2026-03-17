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
    var resetFromRecovery by remember { mutableStateOf(false) }
    val nav = rememberNavController()

    val start = remember {
        when {
            !vm.isPasscodeEnabled() -> Routes.Setup
            vm.shouldRequirePassOnLaunch() -> Routes.Unlock
            else -> Routes.Home
        }
    }

    NavHost(navController = nav, startDestination = start) {

        composable(Routes.Setup) {
            PasscodeSetupScreen(
                vm = vm,
                onCancel = {
                    resetFromRecovery = false
                    nav.popBackStack()
                },
                onNext = {
                    val ok = vm.onFirstEntryComplete()
                    if (ok) {
                        nav.navigate(Routes.Confirm)
                    }
                },
                title = if (resetFromRecovery) "Create a new Password" else "Enter Passcode"
            )
        }

        composable(Routes.Confirm) {
            PasscodeConfirmScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onConfirmed = {
                    val result = vm.onConfirmComplete()
                    if (result is ConfirmResult.Success) {
                        resetFromRecovery = false
                        nav.navigate(Routes.Recovery) {
                            popUpTo(Routes.Setup) { inclusive = true }
                        }
                    } else {

                    }
                }
            )
        }

        composable(Routes.Recovery) {
            RecoveryQuestionScreen(
                vm = vm,
                onSaved = {
                    resetFromRecovery = false
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
                },
                onResetPasscode = {
                    resetFromRecovery = true
                    vm.clearSavedPasscodeForRecovery()
                    vm.resetInput()
                    vm.clearError()
                    nav.navigate(Routes.Setup) {
                        popUpTo(Routes.Unlock) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home) {
            HomeScreen()
        }
    }
}