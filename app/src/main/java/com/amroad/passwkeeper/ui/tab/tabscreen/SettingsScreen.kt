package com.amroad.passwkeeper.ui.tab.tabscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.ui.helper.shareApp

private val HeeboRegular = FontFamily(Font(R.font.heebo_regular))

@Composable
fun SettingsScreen(
    requirePassOnLaunch: Boolean,
    onRequirePassOnLaunchChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onPrivacySafetyClick: () -> Unit,
    onTellAFriendClick: () -> Unit,
) {
    val bg = Color(0xFFE7E7E7)
    val darkText = Color(0xFF1F1F1F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 210.dp)
        ) {
            TopSettingsCard(
                requirePassOnLaunch = requirePassOnLaunch,
                onRequirePassOnLaunchChange = onRequirePassOnLaunchChange,
                onChangePasswordClick = onChangePasswordClick,
                textColor = darkText
            )

            Spacer(modifier = Modifier.height(182.dp))

            GreenActionRow(
                title = "Privacy & Safety",
                onClick = onPrivacySafetyClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            GreenActionRow(
                title = "Tell a Friend",
                onClick = onTellAFriendClick,
                backgroundColor = Color(0xFF3FB34F)
            )
        }
    }
}

@Composable
private fun TopSettingsCard(
    requirePassOnLaunch: Boolean,
    onRequirePassOnLaunchChange: (Boolean) -> Unit,
    onChangePasswordClick: () -> Unit,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 28.dp,
                bottomEnd = 28.dp
            ),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(88.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SettingsSwitchRow(
                    title = "Require Password on app launch",
                    checked = requirePassOnLaunch,
                    onCheckedChange = onRequirePassOnLaunchChange,
                    textColor = textColor,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }

        BlueActionRow(
            title = "Change Password",
            onClick = onChangePasswordClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun BlueActionRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFF2448D8),
        shape = RoundedCornerShape(36.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = modifier
            .height(46.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = HeeboRegular,
                    fontWeight = FontWeight(400),
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 18.dp)
                    .rotate(180f)
                    .size(22.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
private fun GreenActionRow(
    title: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF2448D8)
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(999.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                color = Color.White,
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontWeight = FontWeight(400),
                    fontSize = 15.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 26.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 18.dp)
                    .rotate(180f)
                    .size(22.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = textColor,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = HeeboRegular,
                fontWeight = FontWeight(400)
            ),
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF52D35E),
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFC8C8C8),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun PrivacySafetyDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = "Privacy & Safety",
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    color = Color(0xFF1F1F1F)
                )
            )
        },
        text = {
            Text(
                text = "Your passwords and personal data are stored locally on your device.\n\n" +
                        "We do not share your saved information with third parties.\n\n" +
                        "For better protection, keep your app password secure and do not share it with anyone.",
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontWeight = FontWeight(400),
                    fontSize = 15.sp,
                    color = Color(0xFF444444)
                )
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    style = TextStyle(
                        fontFamily = HeeboRegular,
                        fontWeight = FontWeight(700),
                        fontSize = 15.sp,
                        color = Color(0xFF2448D8)
                    )
                )
            }
        }
    )
}

@Composable
fun SettingsTabScreen(
    requirePassOnLaunch: Boolean,
    onRequirePassOnLaunchChange: (Boolean) -> Unit,
    onChangePasswordClick: () -> Unit = {},
    onPrivacySafetyClick: () -> Unit = {},
    onTellAFriendClick: () -> Unit = {},
) {
    val context = LocalContext.current
    var showPrivacyDialog by remember { mutableStateOf(false) }

    SettingsScreen(
        requirePassOnLaunch = requirePassOnLaunch,
        onRequirePassOnLaunchChange = onRequirePassOnLaunchChange,
        onBack = {},
        onChangePasswordClick = onChangePasswordClick,
        onPrivacySafetyClick = {
            showPrivacyDialog = true
            onPrivacySafetyClick()
        },
        onTellAFriendClick = {
            shareApp(context)
            onTellAFriendClick()
        }
    )

    if (showPrivacyDialog) {
        PrivacySafetyDialog(
            onDismiss = { showPrivacyDialog = false }
        )
    }
}
