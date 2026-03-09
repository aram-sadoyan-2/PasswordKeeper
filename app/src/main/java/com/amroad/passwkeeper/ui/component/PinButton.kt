package com.amroad.passwkeeper.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.R

@Composable
fun PinButton(
    isPinned: Boolean,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(
            id = if (isPinned) R.drawable.ic_pin_filled else R.drawable.ic_pin_outline
        ),
        contentDescription = if (isPinned) "Unpin" else "Pin",
        modifier = Modifier
            .size(30.dp)
            .clickable { onClick() }
    )
}