package com.hyprland.settings.parser.filter.utils

enum class TokenBind {
    MODS,
    KEY,
    DISPATCHER,
    PARAMS,
    ERROR ,
    ERROR_KEY ,
    SPECIAL
}

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

data class TokenBindValue(
    val tokenType: TokenBind,
    val value: String,
    val specialKeyword: SpecialKeywords? = null
)