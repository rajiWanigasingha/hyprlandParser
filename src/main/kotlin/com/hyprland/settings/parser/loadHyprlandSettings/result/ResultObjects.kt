package com.hyprland.settings.parser.loadHyprlandSettings.result

import kotlinx.serialization.Serializable

/**
 *
 * ##### [Type]
 *
 * To get a fast look if an operation filed or passed
 *
 */
@Serializable
enum class Type {
    OK, FAIL
}

/**
 * ##### [Tags]
 *
 * Give a collection of common tags for errors to be identified easily.
 *
 */
@Serializable
enum class Tags {
    VALIDATION_ERROR,
    IO_ERROR,
    INTERNAL_ERROR,
    NO_ERROR,
    NO_CONTENT
}

/**
 * ##### [MetaData]
 *
 * provide mata data about result body that gave by [ResultObjects]
 *
 * When error detect [statusCode] will be among 100..500 same as web status code
 * and [type] will fail with given [errorTags] and message for easies of understanding.
 *
 * @param statusCode Type [Int]
 * @param type Type [Type]
 * @param errorTags Type [Tags]
 * @param message Type [String] or `null`
 *
 */
@Serializable
data class MetaData(
    val statusCode: Int,
    val type: Type,
    val errorTags: Tags,
    val message: String? = null
)


/**
 *
 * ##### [ResultObjects]
 *
 * this will give an object of <`T`> that be a type of [MutableList] of results that will be return
 *
 * when [Type] become `FAIL` it most likely recoverable.
 *
 * @param metaData Type of [MetaData]
 * @param body Type of [MutableList]<[T]> or `null`, [T] been granitic type
 *
 */
@Serializable
data class ResultObjects<T>(
    val metaData: MetaData,
    val body: MutableList<T>? = null
)