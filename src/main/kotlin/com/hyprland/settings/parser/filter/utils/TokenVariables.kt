package com.hyprland.settings.parser.filter.utils

data class TokenVariablesValue(
    val tokenType: String = "VARIABLES",
    val name: String,
    val value: String
)