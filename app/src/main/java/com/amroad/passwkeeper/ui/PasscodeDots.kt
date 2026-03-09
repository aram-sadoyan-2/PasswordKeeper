@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.amroad.passwkeeper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasscodeDots(
    filled: Int,
    total: Int = 6,
    modifier: Modifier = Modifier
) {
    val spacing = 10.dp

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val boxW = ((maxWidth - spacing * (total - 1)) / total).coerceAtMost(46.dp)
        val boxH = 40.dp

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(total) { index ->
                Box(
                    modifier = Modifier
                        .size(boxW, boxH)
                        .background(Color(0xFF2D2D2D), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (index < filled) {
                        Text("✱", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}