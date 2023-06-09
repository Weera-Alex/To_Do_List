package com.example.to_dolist.canva

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CustomAddButtonDesign() {
    Canvas(modifier = Modifier.size(60.dp).background(Color.DarkGray)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val offset = 40f
        val strokeWidth = 10f
        val color = Color.LightGray

        drawLine(color, Offset(0f, 0f), Offset(0f, offset), strokeWidth)
        drawLine(color, Offset(0f, 0f), Offset(offset, 0f), strokeWidth)
        drawLine(color, Offset(canvasWidth, 0f), Offset(canvasWidth - offset, 0f), strokeWidth)
        drawLine(color, Offset(canvasWidth, 0f), Offset(canvasWidth, offset), strokeWidth)
        drawLine(color, Offset(0f, canvasHeight), Offset(0f, canvasHeight - offset), strokeWidth)
        drawLine(color, Offset(0f, canvasHeight), Offset(offset, canvasHeight), strokeWidth)
        drawLine(
            color,
            Offset(canvasWidth, canvasHeight),
            Offset(canvasWidth, canvasHeight - offset),
            strokeWidth
        )
        drawLine(
            color,
            Offset(canvasWidth, canvasHeight),
            Offset(canvasWidth - offset, canvasHeight),
            strokeWidth
        )
        drawLine(
            color,
            Offset(canvasWidth / 2,offset),
            Offset(canvasWidth / 2,canvasHeight - offset),
            strokeWidth
        )
        drawLine(
            color,
            Offset(offset, canvasHeight / 2),
            Offset(canvasWidth - offset,canvasHeight / 2),
            strokeWidth
        )
    }
}