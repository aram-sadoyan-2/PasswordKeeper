package com.amroad.passwkeeper.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.ui.helper.SwipeActionsRow
import kotlin.math.roundToInt

private enum class DragValue {
    Closed,
    Open
}

@Composable
fun VaultFolderSwipeItem(
    title: String,
    subtitle: String = "Folder",
    isPinned: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    onSelect: () -> Unit,
    onPinClick: () -> Unit,
    onRename: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val actionsWidthPx = remember { 320f }
    val density = LocalDensity.current
    val decayAnimation = rememberSplineBasedDecay<Float>()
    val itemHeight = 96.dp

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Closed,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimation,
            confirmValueChange = { true }
        )
    }

    SideEffect {
        state.updateAnchors(
            DraggableAnchors {
                DragValue.Closed at 0f
                DragValue.Open at -actionsWidthPx
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(itemHeight)
    ) {
        SwipeActionsRow(
            onRename = onRename,
            onCopy = onCopy,
            onDelete = onDelete,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .offset {
                    IntOffset(
                        x = state.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal
                )
        ) {
            VaultFolderCard(
                title = title,
                subtitle = subtitle,
                isPinned = isPinned,
                isSelected = isSelected,
                onClick = onClick,
                onSelect = onSelect,
                onPinClick = onPinClick,
            )
        }
    }
}