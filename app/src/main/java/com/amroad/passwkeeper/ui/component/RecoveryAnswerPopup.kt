package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.amroad.passwkeeper.R

@Composable
fun RecoveryAnswerPopup(
    question: String,
    answer: String,
    answerError: String?,
    onAnswerChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_popup_close),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                    )

                    Text(
                        text = "Password Recovery",
                        modifier = Modifier.align(Alignment.Center),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.heebo_regular)),
                            fontWeight = FontWeight.W700,
                            color = Color(0xFF000000)
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_popup_done),
                        contentDescription = "Save",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterEnd)
                            .clip(CircleShape)
                            .clickable { onSave() }
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = question,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF000000),
                        lineHeight = 21.sp
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp)
                        .background(
                            color = Color(0xFFE8E8E8),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(14.dp)
                ) {
                    BasicTextField(
                        value = answer,
                        onValueChange = onAnswerChange,
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.heebo_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color.Black,
                            lineHeight = 22.sp
                        ),
                        decorationBox = { innerTextField ->
                            if (answer.isEmpty()) {
                                Text(
                                    text = "Type your answer...",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF9A9A9A)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                answerError?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    )
                }
            }
        }
    }
}