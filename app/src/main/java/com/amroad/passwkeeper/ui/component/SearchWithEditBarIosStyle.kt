package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun SearchWithEditBarIosStyle(
    value: String,
    onValueChange: (String) -> Unit,
    editText: String = "Edit",
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppSearchField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .defaultMinSize(minHeight = 30.dp)
                .clickable { onEditClick() }
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = editText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.heebo_semibold)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF1D42D9),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun AppSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color(0xFF1D42D9),
        backgroundColor = Color(0xFF1D42D9).copy(alpha = 0.35f)
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = Color(0xFF1C1C1E)
            ),
            keyboardOptions = KeyboardOptions.Default,
            cursorBrush = SolidColor(Color(0xFF1D42D9)),
            modifier = modifier,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF3F3F4),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .defaultMinSize(minHeight = 36.dp)
                        .padding(horizontal = 12.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        modifier = Modifier.size(16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 15.sp,
                                color = Color(0xFF979797),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.heebo_semibold)),
                                    fontWeight = FontWeight.W400,
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}