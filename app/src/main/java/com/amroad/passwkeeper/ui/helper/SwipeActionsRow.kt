package com.amroad.passwkeeper.ui.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

@Composable
fun SwipeActionsRow(
    onRename: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(40.dp))
            .background(Color(0xFFD9D9D9))
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .padding(
                    start = 70.dp,   // empty gray area on the left
                    end = 18.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionPill(
                text = "Rename",
                background = Color(0xFF9EA4AB),
                onClick = onRename,
            )

            ActionPill(
                text = "Copy",
                background = Color(0xFF93C96F),
                onClick = onCopy,
            )

            ActionPill(
                text = "Delete",
                background = Color(0xFFF77E5B),
                onClick = onDelete,
            )
        }
    }
}

@Composable
private fun ActionPill(
    text: String,
    background: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.heebo_semibold)),
                fontWeight = FontWeight.W600,
                color = Color.White
            )
        )
    }
}