package com.amroad.passwkeeper.ui.helper

import android.content.Context
import android.content.Intent

fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val appLink = "https://play.google.com/store/apps/details?id=$appPackageName"

    val shareText = buildString {
        append("Check out this app:\n")
        append(appLink)
    }

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    val chooser = Intent.createChooser(sendIntent, "Tell a Friend")
    context.startActivity(chooser)
}
