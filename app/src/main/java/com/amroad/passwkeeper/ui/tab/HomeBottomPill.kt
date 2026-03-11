package com.amroad.passwkeeper.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

@Composable
fun HomeBottomPill(
    selected: HomeTab,
    onSelect: (HomeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerShape = RoundedCornerShape(999.dp)
    val selectedBlue = Color(0xFF1D42D9)
    val bg = Color.White
    val text = Color.Black

    val items = remember {
        listOf(
            TabItem(HomeTab.VAULT, "VAULT", R.drawable.ic_tab_vault),
            TabItem(HomeTab.GENERATOR, "GENERATOR", R.drawable.ic_tab_generator),
            TabItem(HomeTab.SETTINGS, "SETTINGS", R.drawable.ic_tab_settings),
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(57.dp)
            .clip(containerShape),
        shape = containerShape,
        color = bg,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.tab == selected
                val itemShape = RoundedCornerShape(999.dp)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(itemShape)
                        .background(if (isSelected) selectedBlue else Color.Transparent)
                        .clickable { onSelect(item.tab) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(22.dp),
                            tint = if (isSelected) Color.White else Color.Black
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = item.title,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.heebo_semibold)),
                                fontWeight = FontWeight.W600,
                                color = if (isSelected) Color.White else text,
                                textAlign = TextAlign.Center,
                                lineHeight = 12.sp,
                            )
                        )
                    }
                }
            }
        }
    }
}