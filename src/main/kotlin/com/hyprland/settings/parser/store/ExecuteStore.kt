package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenExecuteValue

/**
 *
 * ##### [ExecuteStore]
 *
 * This will store and return all filtered `execute binds`.
 *
 * ###### Methods
 *
 * [addToExec], This method adds new execute record into store.
 *
 * [getAllExecute], This will return all stored `execute` records
 *
 */
object ExecuteStore {

    /**
     * This will store all `execute` settings as [MutableList]<[TokenExecuteValue]>
     */
    private val executeStore : MutableList<TokenExecuteValue> = mutableListOf()

    /**
     *
     * ##### [addToExec]
     *
     * This will store records to [executeStore]
     *
     * @param tokens, Type as [TokenExecuteValue]
     *
     */
    fun addToExec(tokens: TokenExecuteValue) {

        executeStore.add(tokens)
    }

    /**
     *
     * ##### [getAllExecute]
     *
     * This will return every execute settings form store.
     *
     * @return [executeStore] as [MutableList]<[TokenExecuteValue]>
     *
     */
    fun getAllExecute(): MutableList<TokenExecuteValue> {

        return executeStore
    }

}