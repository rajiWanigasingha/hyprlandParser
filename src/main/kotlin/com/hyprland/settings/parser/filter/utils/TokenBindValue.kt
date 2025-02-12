package com.hyprland.settings.parser.filter.utils

/**
 * These contain all-settings type there is for bind settings
 */
enum class TokenBind {
    MODS,
    KEY,
    DISPATCHER,
    PARAMS,
    ERROR ,
    ERROR_KEY ,
    SPECIAL
}

/**
 * This for contain all special args in binds like bind`rlmd`
 */
enum class SpecialKeywords {
    LOCKED,
    RELEASE,
    LONG_PRESS,
    REPEAT,
    NON_CONSUMING,
    MOUSE,
    TRANSPARENT,
    IGNORE_MODS,
    SEPARATE,
    DESCRIPTION,
    BYPASSES
}

/**
 *
 * ###### [TokenBindValue]
 *
 * This used to contain monitor settings value.
 *
 * @param tokenType as [TokenBind] this use to identify the different type of `bind` settings.
 * @param value as [String] this use for store value according to type.
 * @param specialKeyword as [SpecialKeywords] or `null` this use for identify for special args.
 *
 */
data class TokenBindValue(
    val tokenType: TokenBind,
    val value: String,
    val specialKeyword: SpecialKeywords? = null
)