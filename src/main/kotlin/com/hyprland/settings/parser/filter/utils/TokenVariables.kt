package com.hyprland.settings.parser.filter.utils

/**
 *
 * ###### [TokenVariablesValue]
 *
 * This uses for tokenize keywords and actual values of settings.
 *
 * @param tokenType as [String] this have default value of `VARIABLES` does not need to change it.
 * @param name as [String] this will store key of the variable
 * @param value as [String] this will store value of that key
 *
 */
data class TokenVariablesValue(
    val tokenType: String = "VARIABLES",
    val name: String,
    val value: String
)