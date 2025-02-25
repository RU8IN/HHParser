package io.github.ru8in.hhparser

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "HHParser",
    ) {
        App()
    }
}