package com.hyprland.settings.parser.loadHyprlandSettings.result

data class LineObject(
    val error: Boolean,
    val message: String? = null,
    val line: String
)