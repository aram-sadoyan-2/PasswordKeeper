package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.R

@Composable
fun PinButton(
    isPinned: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = if (isPinned) {
                    R.drawable.icon_pinned_fill
                } else {
                    R.drawable.icon_pinned_outline
                }
            ),
            contentDescription = if (isPinned) "Unpin" else "Pin",
            modifier = Modifier.size(21.dp)
        )
    }
}