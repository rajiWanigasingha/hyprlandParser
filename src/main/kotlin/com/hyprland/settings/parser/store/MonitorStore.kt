package com.hyprland.settings.parser.store

import com.hyprland.settings.parser.filter.utils.TokenMonitorValue

/**
 *
 * ##### [MonitorStore]
 *
 * This will store and return all filtered `monitor settings`.
 *
 * ###### Methods
 *
 * [add], This method adds new monitor settings into store.
 *
 * [getAll], This will return all stored monitor records
 *
 */
object MonitorStore {

    /**
     * This will store all monitor settings as [MutableList]<[List]<[TokenMonitorValue]>>
     */
    private val monitorStore : MutableList<List<TokenMonitorValue>> = mutableListOf()

    /**
     *
     * ##### [add]
     *
     * This will store monitor settings into [monitorStore] as [List]<[MonitorStore]>.
     *
     * @param tokens as [List]<[MonitorStore]>
     *
     */
    fun add(tokens: List<TokenMonitorValue>) {
        monitorStore.add(tokens.toList())
    }

    /**
     *
     * ##### [getAll]
     *
     * This will return all monitor record in store.
     *
     * @return [monitorStore] as [MutableList]<[List]<[TokenMonitorValue]>>
     *
     */
    fun getAll(): MutableList<List<TokenMonitorValue>> {
        return monitorStore
    }
}