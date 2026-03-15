package com.amroad.passwkeeper.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.ui.component.RecoveryAnswerPopup
import com.amroad.passwkeeper.viewmodel.PasscodeViewModel

@Composable
fun RecoveryQuestionScreen(
    vm: PasscodeViewModel,
    onSaved: () -> Unit
) {
    val questions = listOf(
        "What was the name of your first school?",
        "What city were you born in?",
        "What was the name of your childhood pet?",
        "What is the name of the street you grew up on?",
        "What is your favorite book or movie?"
    )

    var selectedQuestion by remember { mutableStateOf<String?>(null) }
    var answer by remember { mutableStateOf("") }
    var answerError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Security & Password Recovery",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.heebo_regular)),
                fontWeight = FontWeight.W700,
                color = Color(0xFF1D42D9),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "For your security, the app does not store your password anywhere. If you forget it, access to the app cannot be restored.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.heebo_regular)),
                fontWeight = FontWeight.W700,
                color = Color(0xFF1D42D9),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "We recommend setting a security question to help you regain access if needed.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.heebo_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF1D42D9),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "CHOOSE A SECURITY QUESTION",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.heebo_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        questions.forEach { question ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        selectedQuestion = question
                        answer = ""
                        answerError = null
                    },
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = question,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.heebo_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF000000),
                            lineHeight = 16.sp
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Open question",
                        modifier = Modifier
                            .size(30.dp)
                            .rotate(180f)
                    )
                }
            }
        }
    }

    selectedQuestion?.let { question ->
        RecoveryAnswerPopup(
            question = question,
            answer = answer,
            answerError = answerError,
            onAnswerChange = {
                answer = it
                answerError = null
            },
            onDismiss = {
                selectedQuestion = null
                answer = ""
                answerError = null
            },
            onSave = {
                val trimmed = answer.trim()

                if (trimmed.isBlank()) {
                    answerError = "Answer cannot be empty"
                    return@RecoveryAnswerPopup
                }

                vm.saveRecoveryQuestion(question, trimmed)

                selectedQuestion = null
                answer = ""
                answerError = null

                onSaved()
            }
        )
    }
}

