package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity
import com.amroad.passwkeeper.ui.component.PinButton

@Composable
 fun FolderItemRow(
    item: VaultItemEntity,
    isHidden: Boolean,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF6F6F6),
                shape = RoundedCornerShape(32.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PinButton(
            isPinned = item.isPinned,
            onClick = onPinClick
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_bold)),
                    fontWeight = FontWeight.W700,
                    color = Color.Black
                )
            )

            Text(
                text = "${item.notePrimaryName}: ${
                    if (isHidden) "••••••••" else item.notePrimaryValue
                }",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
            )

            Text(
                text = "${item.noteSecondaryName}: ${
                    if (isHidden) "••••••••" else item.noteSecondaryValue
                }",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
            )

            if (item.noteAdditional.isNotBlank()) {
                Text(
                    text = item.noteAdditional,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0x99000000)
                    )
                )
            }
        }

        Text(
            text = "Delete",
            color = Color.Red,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.heebo_regular)),
            modifier = Modifier.clickable { onDeleteClick() }
        )
    }
}