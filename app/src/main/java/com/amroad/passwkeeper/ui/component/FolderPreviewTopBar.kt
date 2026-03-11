package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

@Composable
fun FolderPreviewTopBar(
    title: String,
    subtitle: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column {
            androidx.compose.material3.Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_bold)),
                    fontWeight = FontWeight.W700,
                    color = Color(0xFF111111)
                )
            )

            androidx.compose.material3.Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF2C2C2C)
                )
            )
        }
    }
}