package com.amroad.passwkeeper.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import com.amroad.passwkeeper.R

@Composable
fun PasscodeKeypad(
    enabled: Boolean = true,
    onDigit: (Int) -> Unit,
    onBackspace: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stroke = BorderStroke(1.dp, Color(0xFFBDBDBD))

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Key(label = "1", stroke = stroke, enabled = enabled) { onDigit(1) }
            Key(label = "2", stroke = stroke, enabled = enabled) { onDigit(2) }
            Key(label = "3", stroke = stroke, enabled = enabled) { onDigit(3) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Key(label = "4", stroke = stroke, enabled = enabled) { onDigit(4) }
            Key(label = "5", stroke = stroke, enabled = enabled) { onDigit(5) }
            Key(label = "6", stroke = stroke, enabled = enabled) { onDigit(6) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Key(label = "7", stroke = stroke, enabled = enabled) { onDigit(7) }
            Key(label = "8", stroke = stroke, enabled = enabled) { onDigit(8) }
            Key(label = "9", stroke = stroke, enabled = enabled) { onDigit(9) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            EmptyKey(stroke)
            Key(label = "0", stroke = stroke, enabled = enabled) { onDigit(0) }

            Key(
                label = "Backspace",
                iconRes = R.drawable.ic_back_remove,
                stroke = stroke,
                enabled = enabled
            ) {
                onBackspace()
            }
        }
    }
}

@Composable
private fun EmptyKey(stroke: BorderStroke) {
    Surface(
        modifier = Modifier.size(72.dp),
        shape = CircleShape,
        border = stroke,
        color = Color.Transparent
    ) {}
}

@Composable
private fun Key(
    stroke: BorderStroke,
    enabled: Boolean,
    label: String? = null,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        shape = CircleShape,
        border = stroke,
        color = Color.Transparent
    ) {
        Box(contentAlignment = Alignment.Center) {
            when {
                iconRes != null -> {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = label ?: "key",
                        modifier = Modifier.size(26.dp),
                        tint = Color(0xFF1C1C1C) // if PNG colored: use Color.Unspecified
                    )
                }

                label != null -> {
                    Text(text = label, fontSize = 36.sp, color = Color(0xFF1C1C1C))
                }
            }
        }
    }
}