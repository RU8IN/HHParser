package io.github.ru8in.hhparser

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.slf4j.LoggerFactory

fun main() = application {
    val height = 400
    val width = 300
    val xPos = 1920 / 2 - (height / 2)
    val yPos = 1080 / 2 - (width / 2)
    val state = rememberWindowState(
        size = DpSize(height.dp, width.dp),
        position = WindowPosition(xPos.dp, yPos.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "HHParser",
        state = state
    ) {
        App()
    }
}