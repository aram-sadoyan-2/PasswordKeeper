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
    val selectedBlue = Color(0xFF1E44D9)
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(22.dp),
                            tint = if (isSelected) Color.White else Color.Black
                        )
                        Spacer(Modifier.height(4.dp).background(Color.Red))
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            color = if (isSelected) Color.White else text,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}