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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LengthRow(
                value = passwordLength,
                onValueChange = { passwordLength = it }
            )

            OptionRow(
                title = "Lowercase Letter",
                checked = lowercaseEnabled,
                onCheckedChange = { lowercaseEnabled = it }
            )

            OptionRow(
                title = "Uppercase Letter",
                checked = uppercaseEnabled,
                onCheckedChange = { uppercaseEnabled = it }
            )

            OptionRow(
                title = "Numbers",
                checked = numbersEnabled,
                onCheckedChange = { numbersEnabled = it }
            )

            OptionRow(
                title = "Special Characters",
                subtitle = "e.g. - #$%^&",
                checked = specialEnabled,
                onCheckedChange = { specialEnabled = it }
            )

            OptionRow(
                title = "Similar Characters",
                subtitle = "e.g - O and 0",
                checked = similarEnabled,
                onCheckedChange = { similarEnabled = it }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    // generate password here
                },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2448D8),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Generate Password",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun LengthRow(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lenght",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF1D1D1D)
                )
            )

            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 4f..32f,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 8.dp)
            )

            Text(
                text = value.toInt().toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }

        DividerLine()
    }
}

@Composable
private fun OptionRow(
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
                .padding(vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xFF1D1D1D)
                    )
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF5A72E8)
                        )
                    )
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF4CD35B),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFBDBDBD),
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        DividerLine()
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF8F8F8F))
    )
}
