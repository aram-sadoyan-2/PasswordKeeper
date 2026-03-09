package com.amroad.passwkeeper.ui.tab.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.IosShare

@Composable
fun SettingsScreen(
    requirePassOnLaunch: Boolean,
    onRequirePassOnLaunchChange: (Boolean) -> Unit,

    onBack: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onPrivacySafetyClick: () -> Unit,
    onTellAFriendClick: () -> Unit,
) {
    val bg = Color(0xFFE9E9E9)
    val card = Color(0xFF2F2F2F)
    val white = Color.White
    val muted = Color(0xFFBDBDBD)
    val green = Color(0xFF57A84C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        // Top bar (back + centered title)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, start = 10.dp, end = 10.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF2D2D2D))
            }

            Text(
                text = "Settings",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 110.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // Top card: Change password + Toggle row
            Surface(
                color = card,
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Column(Modifier.fillMaxWidth()) {

                    SettingsRow(
                        title = "Change   password",
                        titleColor = white,
                        right = {
                            Icon(
                                Icons.Filled.ChevronRight,
                                contentDescription = null,
                                tint = white
                            )
                        },
                        onClick = onChangePasswordClick
                    )

                    Divider(color = Color(0xFF3C3C3C), thickness = 1.dp)

                    SettingsRow(
                        title = "Require password on app launch",
                        titleColor = muted,
                        right = {
                            PillToggle(
                                checked = requirePassOnLaunch,
                                onCheckedChange = onRequirePassOnLaunchChange
                            )
                        },
                        onClick = { onRequirePassOnLaunchChange(!requirePassOnLaunch) }
                    )
                }
            }

            // Privacy & Safety row
            Surface(
                color = card,
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                SettingsRow(
                    title = "Privacy & Safety",
                    titleColor = white,
                    right = {
                        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = white)
                    },
                    onClick = onPrivacySafetyClick
                )
            }

            // Tell a Friend button
            Surface(
                color = green,
                shape = RoundedCornerShape(22.dp),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clickable(onClick = onTellAFriendClick)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Tell a Friend", color = Color.White, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        Icons.Filled.IosShare,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    titleColor: Color,
    right: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = titleColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        right()
    }
}


@Composable
private fun PillToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val bg = if (checked) Color(0xFF57A84C) else Color(0xFF666666)

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (checked) "ON" else "OFF",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(999.dp),
            color = Color.White,
            modifier = Modifier.size(18.dp)
        ) {
            // simple check
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (checked) "✓" else "",
                    color = Color(0xFF2D2D2D),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun SettingsTabScreen(
    requirePassOnLaunch: Boolean,
    onRequirePassOnLaunchChange: (Boolean) -> Unit,
    onChangePasswordClick: () -> Unit = {},
    onPrivacySafetyClick: () -> Unit = {},
    onTellAFriendClick: () -> Unit = {},
) {
    SettingsScreen(
        requirePassOnLaunch = requirePassOnLaunch,
        onRequirePassOnLaunchChange = onRequirePassOnLaunchChange,
        onBack = {}, // not used in tab
        onChangePasswordClick = onChangePasswordClick,
        onPrivacySafetyClick = onPrivacySafetyClick,
        onTellAFriendClick = onTellAFriendClick
    )
}