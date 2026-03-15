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
    val itemHeight = 48.dp
    val maxItemWidth = 46.dp

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val itemWidth = ((maxWidth - spacing * (total - 1)) / total)
            .coerceAtMost(maxItemWidth)

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(total) { index ->
                Box(
                    modifier = Modifier
                        .size(width = itemWidth, height = itemHeight)
                        .background(
                            color = Color(0xFF2D2D2D),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (index < filled) {
                        Text(
                            text = "✱",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
