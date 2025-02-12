package com.hyprland.settings.parser.filter.utils

/**
 * This creates type of settings that can be in monitor settings.
 */
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

/**
 *
 * ###### [TokenVariablesValue]
 *
 * This used to contain monitor settings value.
 *
 * @param tokenType as [TokensMonitor] this use to identify the different type of monitor settings.
 * @param value as [String] this use for store value according to type.
 *
 */
data class TokenMonitorValue(
    val tokenType: TokensMonitor,
    val value: String
)