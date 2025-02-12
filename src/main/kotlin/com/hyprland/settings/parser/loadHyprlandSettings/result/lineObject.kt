package com.hyprland.settings.parser.loadHyprlandSettings.result

/**
 *
 * ##### [LineObject]
 *
 * This use to load a hyprland settings from the file.
 *
 * If there is an invalid format it will make [error] True
 *
 * @param error Type [Boolean]
 * @param message Type [String] or `null`
 * @param line Type [String]
 *
 */
data class LineObject(
    val error: Boolean,
    val message: String? = null,
    val line: String
)