package com.amroad.passwkeeper.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import com.amroad.passwkeeper.R

@Composable
fun RenamePopupDialogSingle(
    visible: Boolean,
    initialTitle: String = "",
    initialSubtitle: String = "",
    onDismiss: () -> Unit,
    onConfirm: (title: String, subtitle: String) -> Unit,
    @DrawableRes closeIconRes: Int = R.drawable.ic_popup_close,
    @DrawableRes doneIconRes: Int = R.drawable.ic_popup_done,
) {
    if (!visible) return

    var title by remember(initialTitle, visible) {
        mutableStateOf(TextFieldValue(initialTitle))
    }
    var subtitle by remember(initialSubtitle, visible) {
        mutableStateOf(TextFieldValue(initialSubtitle))
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(34.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleIconButton(
                        iconRes = closeIconRes,
                        onClick = onDismiss,
                        size = 36.dp
                    )

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Rename",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    CircleIconButton(
                        iconRes = doneIconRes,
                        onClick = {
                            onConfirm(title.text, subtitle.text)
                        },
                        size = 36.dp
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                PopupEditText(
                    value = title,
                    placeholder = "Title",
                    clearIconRes = closeIconRes,
                    onValueChange = { title = it },
                    onClearClick = { title = TextFieldValue("") }
                )

                Spacer(modifier = Modifier.height(18.dp))

                PopupEditText(
                    value = subtitle,
                    placeholder = "Subtitle",
                    clearIconRes = closeIconRes,
                    onValueChange = { subtitle = it },
                    onClearClick = { subtitle = TextFieldValue("") }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun PopupEditText(
    value: TextFieldValue,
    placeholder: String,
    @DrawableRes clearIconRes: Int,
    onValueChange: (TextFieldValue) -> Unit,
    onClearClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF0F0F0))
            .padding(vertical = 6.dp).padding(start = 20.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF4A4A4A),
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF7A7A7A),
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )

       // if (value.text.isNotEmpty()) {
           // Spacer(modifier = Modifier.size(10.dp))

            CircleIconButton(
                iconRes = clearIconRes,
                onClick = onClearClick,
            )
       // }
    }
}

@Composable
private fun CircleIconButton(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    size: Dp = 26.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(size)
        )
    }
}

@Composable
fun RenamePopupDemoSingle() {
    var showDialog by remember { mutableStateOf(true) }

    RenamePopupDialog(
        visible = showDialog,
        initialTitle = "",
        initialSubtitle = "",
        onDismiss = { showDialog = false },
        onConfirm = { title, subtitle ->
            showDialog = false
            // save data here
        }
    )
}