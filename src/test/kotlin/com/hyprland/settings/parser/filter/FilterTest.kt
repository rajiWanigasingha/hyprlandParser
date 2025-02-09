package com.hyprland.settings.parser.filter

import com.hyprland.settings.parser.filter.utils.TokenMonitorValue
import com.hyprland.settings.parser.loadHyprlandSettings.LoadHyprlandSettings
import com.hyprland.settings.parser.loadHyprlandSettings.result.Type
import com.hyprland.settings.parser.store.BindStore
import com.hyprland.settings.parser.store.ExecuteStore
import com.hyprland.settings.parser.store.MonitorStore
import com.hyprland.settings.parser.store.VariableStore
import org.slf4j.LoggerFactory
import kotlin.test.Test

class FilterTest {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val load = LoadHyprlandSettings().load().getOrThrow()

    @Test
    fun `Filter thought hyprland settings file`() {
        runCatching {

            if (load.metaData.type == Type.OK) {

                load.body
                    .takeIf { !it.isNullOrEmpty() }
                    ?.let {

                        Filter(it).sortAndFilter()

                    }

            }

            MonitorStore.getAll().forEach { tokenMonitorValues: List<TokenMonitorValue> ->
                logger.info(tokenMonitorValues.toString())
            }

            ExecuteStore.getAllExecute().forEach { tokenExecuteValue ->
                logger.info(tokenExecuteValue.toString())
            }

            BindStore.getAllBind().forEach { tokenBindValues ->
                logger.info(tokenBindValues.toString())
            }

            VariableStore.getAllVariable().forEach { tokenVariablesValue ->
                logger.info(tokenVariablesValue.toString())
            }
        }
    }


}