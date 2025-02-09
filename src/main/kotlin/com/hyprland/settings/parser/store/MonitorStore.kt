package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenMonitorValue

object MonitorStore {
    private val monitorStore : MutableList<List<TokenMonitorValue>> = mutableListOf()

    fun add(tokens: List<TokenMonitorValue>) {
        monitorStore.add(tokens.toList())
    }

    fun getAll(): MutableList<List<TokenMonitorValue>> {
        return monitorStore
    }
}