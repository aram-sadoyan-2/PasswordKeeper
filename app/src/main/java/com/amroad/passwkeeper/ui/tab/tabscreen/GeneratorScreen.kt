package com.amroad.passwkeeper.ui.screen.generator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.data.local.entity.GeneratedPasswordEntity
import com.amroad.passwkeeper.ui.screen.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

private val HeeboRegular = FontFamily(Font(R.font.heebo_regular))
private val HeeboMedium = FontFamily(Font(R.font.heebo_medium))
private val HeeboBold = FontFamily(Font(R.font.heebo_bold))

data class GeneratedPasswordUi(
    val id: Long = 0,
    val title: String,
    val password: String,
    val strength: PasswordStrength
)

fun GeneratedPasswordEntity.toUi(): GeneratedPasswordUi {
    return GeneratedPasswordUi(
        id = id,
        title = title,
        password = password,
        strength = when (strength) {
            "STRONG" -> PasswordStrength.STRONG
            "NORMAL" -> PasswordStrength.NORMAL
            else -> PasswordStrength.WEAK
        }
    )
}

enum class PasswordStrength(
    val label: String,
    val color: Color
) {
    WEAK("Weak", Color(0xFFF04444)),
    NORMAL("Normal", Color(0xFFE4C400)),
    STRONG("Strong", Color(0xFF41C95C))
}

@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = koinViewModel()
    //onAddToClick: (GeneratedPasswordUi) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE7E7E7))
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        PasswordLengthRow(
            value = uiState.passwordLength,
            onValueChange = viewModel::onPasswordLengthChange
        )

        GeneratorOptionRow(
            title = "Lowercase Letter",
            checked = uiState.lowercaseEnabled,
            onCheckedChange = viewModel::onLowercaseChange
        )

        GeneratorOptionRow(
            title = "Uppercase Letter",
            checked = uiState.uppercaseEnabled,
            onCheckedChange = viewModel::onUppercaseChange
        )

        GeneratorOptionRow(
            title = "Numbers",
            checked = uiState.numbersEnabled,
            onCheckedChange = viewModel::onNumbersChange
        )

        GeneratorOptionRow(
            title = "Special Characters",
            subtitle = "e.g. - #$%^&",
            checked = uiState.specialEnabled,
            onCheckedChange = viewModel::onSpecialChange
        )

        GeneratorOptionRow(
            title = "Similar Characters",
            subtitle = "e.g - O and 0",
            checked = uiState.similarEnabled,
            onCheckedChange = viewModel::onSimilarChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = viewModel::generatePassword,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(184.dp)
                .height(40.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2448D8),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Generate Password",
                style = TextStyle(
                    fontFamily = HeeboMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = uiState.generatedPasswords,
                key = { item -> item.id }
            ) { item ->
                GeneratedPasswordCard(
                    item = item,
                    onCopyClick = {
                        clipboardManager.setText(AnnotatedString(item.password))
                    },
                    onAddToClick = {
                      //  onAddToClick(item)
                    }
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lenght",
                style = TextStyle(
                    fontFamily = HeeboRegular,
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 10.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = HeeboRegular,
                        fontSize = 16.sp,
                        color = Color(0xFF1F1F1F)
                    )
                )

                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        style = TextStyle(
                            fontFamily = HeeboRegular,
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
private fun GeneratedPasswordCard(
    item: GeneratedPasswordUi,
    onCopyClick: () -> Unit,
    onAddToClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "✎",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color(0xFF3A3A3A)
                )
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = item.title,
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A)
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(7.dp)
                    .height(7.dp)
                    .background(
                        color = item.strength.color,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = item.strength.label,
                style = TextStyle(
                    fontFamily = HeeboRegular,
                    fontSize = 15.sp,
                    color = Color(0xFF4A4A4A)
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .background(
                        color = Color(0xFFDCDCDC),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item.password,
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = HeeboBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF2C2C2C)
                    )
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            SmallActionButton(
                text = "Add to",
                onClick = onAddToClick
            )

            Spacer(modifier = Modifier.width(6.dp))

            SmallActionButton(
                text = "Copy",
                onClick = onCopyClick
            )
        }
    }
}

@Composable
private fun SmallActionButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .background(
                color = Color(0xFF2F54E4),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = HeeboMedium,
                fontSize = 14.sp,
                color = Color.White
            )
        )
    }
}

@Composable
private fun GeneratorDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color(0xFF9B9B9B)
    )
}

fun generatePassword(
    length: Int,
    useLowercase: Boolean,
    useUppercase: Boolean,
    useNumbers: Boolean,
    useSpecial: Boolean,
    includeSimilar: Boolean
): String {
    if (!useLowercase && !useUppercase && !useNumbers && !useSpecial) {
        return ""
    }

    val lowercase = if (includeSimilar) {
        "abcdefghijklmnopqrstuvwxyz"
    } else {
        "abcdefghijkmnpqrstuvwxyz"
    }

    val uppercase = if (includeSimilar) {
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    } else {
        "ABCDEFGHJKLMNPQRSTUVWXYZ"
    }

    val numbers = if (includeSimilar) {
        "0123456789"
    } else {
        "23456789"
    }

    val special = "@#\$%&*!?/+-_=()[]{}"

    val pool = buildString {
        if (useLowercase) append(lowercase)
        if (useUppercase) append(uppercase)
        if (useNumbers) append(numbers)
        if (useSpecial) append(special)
    }

    if (pool.isEmpty()) return ""

    return (1..length)
        .map { pool.random() }
        .joinToString("")
}

fun calculatePasswordStrength(
    password: String,
    useLowercase: Boolean,
    useUppercase: Boolean,
    useNumbers: Boolean,
    useSpecial: Boolean
): PasswordStrength {
    var score = 0

    if (password.length >= 8) score++
    if (password.length >= 12) score++
    if (password.length >= 16) score++

    if (useLowercase) score++
    if (useUppercase) score++
    if (useNumbers) score++
    if (useSpecial) score++

    return when {
        score >= 6 -> PasswordStrength.STRONG
        score >= 4 -> PasswordStrength.NORMAL
        else -> PasswordStrength.WEAK
    }
}
