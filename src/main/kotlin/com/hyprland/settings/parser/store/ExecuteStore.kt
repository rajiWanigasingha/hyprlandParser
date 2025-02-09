package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenExecuteValue

object ExecuteStore {
    private val executeStore : MutableList<TokenExecuteValue> = mutableListOf()

    fun addToExec(tokens: TokenExecuteValue) {

        executeStore.add(tokens)
    }

    fun getAllExecute(): MutableList<TokenExecuteValue> {

        return executeStore
    }

}