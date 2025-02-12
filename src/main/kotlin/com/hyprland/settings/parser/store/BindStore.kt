package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenBindValue

/**
 *
 * #### [BindStore]
 *
 * use to store filtered settings of `keybindings` of hyprland.
 *
 * ###### Method that use
 *
 * [addToBind], This will add [List]<[TokenBindValue]> into store.
 *
 * [getAllBind], This will return all records of store as [MutableList]<[List]<[TokenBindValue]>>.
 *
 */
object BindStore {

    /**
     * This will store all keybindings as [List]<[TokenBindValue]>
     */
    private val bindStore: MutableList<List<TokenBindValue>> = mutableListOf()

    /**
     *
     * ###### [addToBind]
     *
     * This will add new record to [bindStore].
     *
     * @param bind Type [List]<[TokenBindValue]>
     *
     */
    fun addToBind(bind: List<TokenBindValue>) {

        bindStore.add(bind.toList())
    }

    /**
     *
     * ###### [getAllBind]
     *
     * This will return all the records of stored keybindings.
     *
     * @return [bindStore]
     *
     */
    fun getAllBind(): MutableList<List<TokenBindValue>> {
        return bindStore
    }

}