package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenVariablesValue

object VariableStore {
    private val variableStore: MutableList<TokenVariablesValue> = mutableListOf()

    fun addToVariable(token: TokenVariablesValue) {
        variableStore.add(token)
    }

    fun getAllVariable(): MutableList<TokenVariablesValue> {
        return variableStore
    }

}