package com.hyprland.settings.parser.filter.utils

import kotlinx.serialization.Serializable

/**
 * This creates type of settings that can be in execute settings.
 */
@Serializable
enum class TokenExecute {
    EXECUTE_ONCE,
    EXECUTE,
    EXECUTE_SHUTDOWN
}

/**
 *
 * ###### [TokenExecuteValue]
 *
 * This used to contain monitor settings value.
 *
 * @param tokenType as [TokenExecute] this use to identify the different type of `execute` settings.
 * @param value as [String] this use for store value according to type.
 *
 */
@Serializable
data class TokenExecuteValue(
    val tokenType: TokenExecute,
    val value: String
)
