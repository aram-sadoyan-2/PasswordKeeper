package com.amroad.passwkeeper.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity
import kotlinx.coroutines.launch

@Composable
fun FolderItemRow(
    item: VaultItemEntity,
    isHidden: Boolean,
    isGlobalEditMode: Boolean,
    globalEditChangeKey: Int,
    onSaveClick: (
        title: String,
        primaryName: String,
        primaryValue: String,
        secondaryName: String,
        secondaryValue: String,
        additionalNote: String
    ) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isLocalEditMode by remember(item.id) { mutableStateOf(false) }
    val isEditMode = isGlobalEditMode || isLocalEditMode

    var title by remember(item.id, item.updatedAt) { mutableStateOf(item.title) }
    var primaryName by remember(item.id, item.updatedAt) { mutableStateOf(item.notePrimaryName) }
    var primaryValue by remember(item.id, item.updatedAt) { mutableStateOf(item.notePrimaryValue) }
    var secondaryName by remember(item.id, item.updatedAt) { mutableStateOf(item.noteSecondaryName) }
    var secondaryValue by remember(item.id, item.updatedAt) { mutableStateOf(item.noteSecondaryValue) }
    var additionalNote by remember(item.id, item.updatedAt) { mutableStateOf(item.noteAdditional) }

    var primaryHiddenOverride by remember(item.id) { mutableStateOf<Boolean?>(null) }
    var secondaryHiddenOverride by remember(item.id) { mutableStateOf<Boolean?>(null) }

    val isPrimaryHidden = primaryHiddenOverride ?: isHidden
    val isSecondaryHidden = secondaryHiddenOverride ?: isHidden

    fun saveIfChanged() {
        val isChanged =
            title != item.title ||
                    primaryName != item.notePrimaryName ||
                    primaryValue != item.notePrimaryValue ||
                    secondaryName != item.noteSecondaryName ||
                    secondaryValue != item.noteSecondaryValue ||
                    additionalNote != item.noteAdditional

        if (isChanged) {
            onSaveClick(
                title,
                primaryName,
                primaryValue,
                secondaryName,
                secondaryValue,
                additionalNote
            )
        }
    }

    var previousGlobalEditMode by remember(item.id) { mutableStateOf(isGlobalEditMode) }

    LaunchedEffect(isGlobalEditMode) {
        if (previousGlobalEditMode && !isGlobalEditMode) {
            saveIfChanged()
            isLocalEditMode = false
        }
        previousGlobalEditMode = isGlobalEditMode
    }

    LaunchedEffect(isHidden) {
        primaryHiddenOverride = null
        secondaryHiddenOverride = null
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 0.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                ItemEditableText(
                    text = title,
                    isEditMode = isEditMode,
                    isTitle = true,
                    onValueChange = { title = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                if (!isGlobalEditMode) {
                    if (isLocalEditMode) {
                        Text(
                            text = "Done",
                            color = Color(0xFF2348DB),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.heebo_bold)),
                                fontWeight = FontWeight.W700
                            ),
                            modifier = Modifier.clickable {
                                saveIfChanged()
                                isLocalEditMode = false
                            }
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                isLocalEditMode = true
                            }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_edit),
                                contentDescription = "Edit"
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Edit",
                                color = Color(0xFF3A3A3A),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.heebo_bold)),
                                    fontWeight = FontWeight.W700
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ItemEditableText(
                text = primaryName,
                isEditMode = isEditMode,
                onValueChange = { primaryName = it },
                modifier = Modifier.padding(start = 12.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                ValueWithTrailingIcon(
                    realValue = primaryValue,
                    isEditMode = isEditMode,
                    isHidden = isPrimaryHidden,
                    trailingIconRes = if (isPrimaryHidden) {
                        R.drawable.ic_password_hidden
                    } else {
                        R.drawable.ic_password_shown
                    },
                    onValueChange = { primaryValue = it },
                    onTrailingClick = {
                        primaryHiddenOverride = !(primaryHiddenOverride ?: isHidden)
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(18.dp))

                CopyButton(
                    onClick = {
                        // copy primaryValue
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ItemEditableText(
                text = secondaryName,
                isEditMode = isEditMode,
                onValueChange = { secondaryName = it },
                modifier = Modifier.padding(start = 12.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                ValueWithTrailingIcon(
                    realValue = secondaryValue,
                    isEditMode = isEditMode,
                    isHidden = isSecondaryHidden,
                    trailingIconRes = if (isSecondaryHidden) {
                        R.drawable.ic_password_hidden
                    } else {
                        R.drawable.ic_password_shown
                    },
                    onValueChange = { secondaryValue = it },
                    onTrailingClick = {
                        secondaryHiddenOverride = !(secondaryHiddenOverride ?: isHidden)
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(18.dp))

                CopyButton(
                    onClick = {
                        // copy secondaryValue
                    }
                )
            }

            if (additionalNote.isNotBlank() || isEditMode) {
                Spacer(modifier = Modifier.height(12.dp))

                ItemEditableText(
                    text = additionalNote,
                    isEditMode = isEditMode,
                    onValueChange = { additionalNote = it }
                )
            }

            if (isEditMode) {
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Delete",
                    color = Color(0xFFFF3535),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_bold)),
                        fontWeight = FontWeight.W700
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onDeleteClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE3E3E3))
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemEditableText(
    text: String,
    isEditMode: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isTitle: Boolean = false,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .wrapContentHeight()
            .bringIntoViewRequester(bringIntoViewRequester),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditMode) {
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (isEditMode) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = true,
                maxLines = 1,
                textStyle = TextStyle(
                    fontSize = if (isTitle) 22.sp else 16.sp,
                    fontFamily = FontFamily(
                        Font(if (isTitle) R.font.heebo_bold else R.font.heebo_regular)
                    ),
                    fontWeight = if (isTitle) FontWeight.W700 else FontWeight.W400,
                    color = Color.Black
                ),
                cursorBrush = SolidColor(Color(0xFF1D42D9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
            )
        } else {
            Text(
                text = text,
                color = Color.Black,
                style = TextStyle(
                    fontSize = if (isTitle) 22.sp else 16.sp,
                    fontFamily = FontFamily(
                        Font(if (isTitle) R.font.heebo_bold else R.font.heebo_regular)
                    ),
                    fontWeight = if (isTitle) FontWeight.W700 else FontWeight.W400
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ValueWithTrailingIcon(
    realValue: String,
    isEditMode: Boolean,
    isHidden: Boolean,
    trailingIconRes: Int,
    onValueChange: (String) -> Unit,
    onTrailingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .background(
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 5.dp)
            .padding(start = 10.dp, end = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditMode) {
                BasicTextField(
                    value = realValue,
                    onValueChange = onValueChange,
                    singleLine = true,
                    maxLines = 1,
                    visualTransformation = if (isHidden) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color.Black
                    ),
                    cursorBrush = SolidColor(Color(0xFF1D42D9)),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                scope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        }
                )
            } else {
                Text(
                    text = if (isHidden) "•".repeat(realValue.length) else realValue,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                        fontWeight = FontWeight.W400
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Image(
                painter = painterResource(trailingIconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onTrailingClick()
                    }
            )
        }
    }
}

@Composable
private fun CopyButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF2348DB),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Copy",
            color = Color.White,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.heebo_bold)),
                fontWeight = FontWeight.W700
            )
        )
    }
}