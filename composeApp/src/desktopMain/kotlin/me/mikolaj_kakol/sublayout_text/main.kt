package me.mikolaj_kakol.sublayout_text

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "sublayout-text",
    ) {
        App()
    }
}