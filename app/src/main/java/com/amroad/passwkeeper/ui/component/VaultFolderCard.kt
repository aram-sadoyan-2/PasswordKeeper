package com.amroad.passwkeeper.ui.component
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

@Composable
fun VaultFolderCard(
    title: String,
    subtitle: String,
    isPinned: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    onSelect: () -> Unit,
    onPinClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    val scale = animateFloatAsState(
        targetValue = if (isPressed.value) 0.98f else 1f,
        label = "card_scale"
    )

    val bgColor = animateColorAsState(
        targetValue = when {
            isPressed.value -> Color(0xFFE2E9FA)
            isSelected -> Color(0xFFEAF0FF)
            else -> Color(0xFFF6F6F6)
        },
        label = "card_bg"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(98.dp)
            .scale(scale.value)
            .clip(RoundedCornerShape(40.dp))
            .background(bgColor.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(start = 11.dp, end = 26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PinButton(
            isPinned = isPinned,
            onClick = onPinClick
        )

        Spacer(modifier = Modifier.width(18.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_bold)),
                    fontWeight = FontWeight.W900,
                    color = Color(0xFF000000)
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_folder),
                contentDescription = "Folder",
                modifier = Modifier.size(44.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "Open",
            modifier = Modifier
                .size(24.dp)
                .rotate(180f)
        )
    }
}