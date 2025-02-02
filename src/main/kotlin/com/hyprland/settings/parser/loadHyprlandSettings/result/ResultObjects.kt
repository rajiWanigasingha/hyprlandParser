package com.hyprland.settings.parser.loadHyprlandSettings.result

import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    OK, FAIL
}

@Serializable
enum class Tags {
    VALIDATION_ERROR,
    IO_ERROR,
    INTERNAL_ERROR,
    NO_ERROR,
    NO_CONTENT
}

@Serializable
data class MetaData(
    val statusCode: Int,
    val type: Type,
    val errorTags: Tags,
    val message: String? = null
)

@Serializable
data class ResultObjects<T>(
    val metaData: MetaData,
    val body: MutableList<T>? = null
)