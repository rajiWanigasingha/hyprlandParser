package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenVariablesValue

/**
 *
 * ##### [VariableStore]
 *
 * This will store and return all filtered `variables settings`.
 *
 * ###### Methods
 *
 * [addToVariable], This method adds new variables settings into store.
 *
 * [getAllVariable], This will return all stored variables records
 *
 */
object VariableStore {

    /**
     * This will store all monitor settings as [MutableList]<[List]<[TokenVariablesValue]>>
     */
    private val variableStore: MutableList<TokenVariablesValue> = mutableListOf()

    /**
     *
     * ##### [addToVariable]
     *
     * This will add new record to [variableStore].
     *
     * @param token as [TokenVariablesValue]
     *
     */
    fun addToVariable(token: TokenVariablesValue) {
        variableStore.add(token)
    }

    /**
     *
     * ##### [getAllVariable]
     *
     * This will return all records in [variableStore]
     *
     * @return [variableStore] as [MutableList]<[TokenVariablesValue]>
     *
     */
    fun getAllVariable(): MutableList<TokenVariablesValue> {
        return variableStore
    }

}