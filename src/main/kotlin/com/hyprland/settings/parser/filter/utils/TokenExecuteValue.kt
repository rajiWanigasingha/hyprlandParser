package com.hyprland.settings.parser.filter.utils

enum class TokenExecute {
    EXECUTE_ONCE,
    EXECUTE,
    EXECUTE_SHUTDOWN
}

data class TokenExecuteValue(
    val tokenType: TokenExecute,
    val value: String
)
