package com.amroad.passwkeeper.ui.screen.generator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R

private val HeeboRegular = FontFamily(Font(R.font.heebo_regular))
private val HeeboMedium = FontFamily(Font(R.font.heebo_medium))
private val HeeboBold = FontFamily(Font(R.font.heebo_bold))

@Composable
fun GeneratorScreen() {
    var passwordLength by remember { mutableFloatStateOf(20f) }
    var lowercaseEnabled by remember { mutableStateOf(true) }
    var uppercaseEnabled by remember { mutableStateOf(true) }
    var numbersEnabled by remember { mutableStateOf(true) }
    var specialEnabled by remember { mutableStateOf(true) }
    var similarEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7E7E7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 44.dp)
        ) {
            PasswordLengthRow(
                value = passwordLength,
                onValueChange = { passwordLength = it }
            )

            GeneratorOptionRow(
                title = "Lowercase Letter",
                checked = lowercaseEnabled,
                onCheckedChange = { lowercaseEnabled = it }
            )

            GeneratorOptionRow(
                title = "Uppercase Letter",
                checked = uppercaseEnabled,
                onCheckedChange = { uppercaseEnabled = it }
            )

            GeneratorOptionRow(
                title = "Numbers",
                checked = numbersEnabled,
                onCheckedChange = { numbersEnabled = it }
            )

            GeneratorOptionRow(
                title = "Special Characters",
                subtitle = "e.g. - #$%^&",
                checked = specialEnabled,
                onCheckedChange = { specialEnabled = it }
            )

            GeneratorOptionRow(
                title = "Similar Characters",
                subtitle = "e.g - O and 0",
                checked = similarEnabled,
                onCheckedChange = { similarEnabled = it }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    // generate password action
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(184.dp)
                    .height(36.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2448D8),
                    contentColor = Color.White
                ),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text(
                    text = "Generate Password",
                    style = TextStyle(
                        fontFamily = HeeboMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
private fun PasswordLengthRow(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Length",
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF1F1F1F)
                )
            )

            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 4f..32f,
                steps = 27,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp, end = 10.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE5E5E5),
                    activeTrackColor = Color(0xFF2448D8),
                    inactiveTrackColor = Color(0xFF2448D8)
                )
            )

            Text(
                text = value.toInt().toString(),
                style = TextStyle(
                    fontFamily = HeeboBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF111111)
                )
            )
        }

        GeneratorDivider()
    }
}

@Composable
private fun GeneratorOptionRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    subtitle: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = HeeboRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color(0xFF1F1F1F)
                    )
                )

                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        style = TextStyle(
                            fontFamily = HeeboRegular,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF6D7EF0)
                        )
                    )
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF53D35D),
                    checkedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFC8C8C8),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        GeneratorDivider()
    }
}

@Composable
private fun GeneratorDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF9B9B9B))
    )
}
