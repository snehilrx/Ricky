package com.snehil.ricky.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED;

    // Factory method that creates an instance of the class based on window width
    companion object {
        fun basedOnWidth(windowWidth: Dp): WindowSize {
            return when {
                windowWidth < 520.dp -> COMPACT
                windowWidth < 620.dp -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}