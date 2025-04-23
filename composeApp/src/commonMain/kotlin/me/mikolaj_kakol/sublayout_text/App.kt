package me.mikolaj_kakol.sublayout_text

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp

private const val MAX_LINES = 5

@Composable
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Comment is less than $MAX_LINES lines")
            ExpandableComment(4)
            Text("Comment is more than $MAX_LINES lines")
            ExpandableComment(8)
        }
    }
}

@Composable
private fun ExpandableComment(lines: Int) {
    var isExpanded by remember { mutableStateOf(false) }
    SubcomposeLayout(Modifier.padding(12.dp)) { constraints ->
        var hasOverflow = false
        val maxLines = if (isExpanded)
            Int.MAX_VALUE
        else
            MAX_LINES
        val reg = subcompose("reg") {
            Text(
                prepareText(lines),
                modifier = Modifier
                    .animateContentSize()
                    .background(Color.LightGray),
                maxLines = maxLines,
                onTextLayout = {
                    hasOverflow = it.hasVisualOverflow
                }
            )
        }.first().measure(constraints)

        val button = if (hasOverflow || isExpanded) {
            subcompose("button") {
                Button(onClick = {
                    isExpanded = !isExpanded
                }) {
                    if (isExpanded) {
                        Text("show less")
                    } else {
                        Text("show more")
                    }
                }
            }.first().measure(constraints)
        } else null


        layout(reg.width, reg.measuredHeight + (button?.height ?: 0)) {
            reg.placeRelative(0, 0)
            button?.placeRelative(0, reg.measuredHeight)
        }
    }
}


private fun prepareText(lines: Int): String {
    return buildString {
        repeat(lines) { this.appendLine("Comment line ${it + 1}") }
    }.trim()
}