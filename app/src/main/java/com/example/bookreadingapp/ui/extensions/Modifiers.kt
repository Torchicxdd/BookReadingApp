package com.example.bookreadingapp.ui.extensions

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange

/***
 * Custom pointer tap gesture to allow tap without swipe being detected
 * Checks to see how much the pointer moved to determine if it was a tap or swipe
 */
fun Modifier.detectedTapWithoutSwipe(
    onTap: () -> Unit,
    movementThreshold: Float = 10f
): Modifier = pointerInput(Unit) {
    awaitEachGesture {
        var pointerMoved = false

        do {
            // Gets the pointer event and loops until no event happens
            val event = awaitPointerEvent()
            val dragAmount = event.changes.first().positionChange()
            if (dragAmount.getDistance() > movementThreshold) {
                pointerMoved = true
                break
            }
        } while (event.changes.any { it.pressed })

        if (!pointerMoved) {
            onTap()
        }
    }
}