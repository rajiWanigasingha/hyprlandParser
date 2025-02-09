package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenBindValue

object BindStore {

    private val bindStore: MutableList<List<TokenBindValue>> = mutableListOf()

    fun addToBind(bind: List<TokenBindValue>) {

        bindStore.add(bind.toList())
    }

    fun getAllBind(): MutableList<List<TokenBindValue>> {
        return bindStore
    }

}