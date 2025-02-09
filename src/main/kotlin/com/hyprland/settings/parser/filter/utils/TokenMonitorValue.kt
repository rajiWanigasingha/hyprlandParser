package com.hyprland.settings.parser.filter.utils

enum class TokensMonitor {
    NAME,
    RESOLUTION_NORMAL,
    RESOLUTION_AUTO,
    RESOLUTION_DISABLE,
    RESOLUTION_MODELINE,
    RESOLUTION_RESERVED,
    POSITION_NORMAL,
    POSITION_AUTO,
    SCALE,
    ERROR,
    TOP,
    BOTTOM,
    RIGHT,
    LEFT,
    EXTRA_MIRROR,
    EXTRA_BIT,
    EXTRA_TRANSFORMATION
}

data class TokenMonitorValue(
    val tokenType: TokensMonitor,
    val value: String
)