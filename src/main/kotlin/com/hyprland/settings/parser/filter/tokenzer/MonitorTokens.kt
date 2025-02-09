package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenMonitorValue
import com.hyprland.settings.parser.filter.utils.TokensMonitor
import com.hyprland.settings.parser.store.MonitorStore
import org.slf4j.LoggerFactory

class MonitorTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val tmpStore = mutableListOf<TokenMonitorValue>()
    private val extraStore = mutableListOf<TokenMonitorValue>()

    fun process(line: String) {

        line
            .trim()
            .substringBefore("#")
            .split(",")
            .let {
                when (it.size) {

                    2 -> disableMonitor(it)

                    4 -> defaultConfig(it)

                    in 4..10 -> reservedArea(it)
                }
            }

    }


    private fun defaultConfig(conf: List<String>, extra: Boolean = false) {

        val defaultRegex = "\"?(\\d{3,4})x(\\d{3,4})(?:@(\\d+))?\"?".toRegex()
        val defaultPosition = "-?\\d+x\\d+".toRegex()
        val defaultScale = "(\\d+(\\.\\d+)?)".toRegex()

        conf.forEachIndexed { index: Int, lineConf: String ->

            when (index) {

                0 -> {
                    val tmpName = lineConf.ifBlank { "Automatic detection" }
                    tmpStore.add(TokenMonitorValue(TokensMonitor.NAME, tmpName))
                }

                1 -> {
                    when {
                        lineConf.isEmpty() -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty resolution"))

                        lineConf in setOf(
                            "preferred",
                            "highres",
                            "highrr"
                        ) -> tmpStore.add(TokenMonitorValue(TokensMonitor.RESOLUTION_AUTO, lineConf))

                        defaultRegex.matches(lineConf) -> tmpStore.add(
                            TokenMonitorValue(
                                TokensMonitor.RESOLUTION_NORMAL,
                                lineConf
                            )
                        )

                        lineConf.startsWith("modeline") -> tmpStore.add(
                            TokenMonitorValue(
                                TokensMonitor.RESOLUTION_MODELINE,
                                lineConf
                            )
                        )

                        else -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Unhandled configuration of resolution"))
                    }

                }

                2 -> {
                    when {
                        lineConf.isEmpty() -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty Position"))

                        defaultPosition.matches(lineConf) -> tmpStore.add(
                            TokenMonitorValue(
                                TokensMonitor.POSITION_NORMAL,
                                lineConf
                            )
                        )

                        lineConf in setOf("auto", "auto-right", "auto-left", "auto-up", "auto-down") -> tmpStore.add(
                            TokenMonitorValue(TokensMonitor.POSITION_AUTO, lineConf)
                        )

                        else -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Unhandled configuration of position"))
                    }
                }

                3 -> {
                    when {
                        lineConf.isEmpty() -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty scale"))

                        defaultScale.matches(lineConf) -> tmpStore.add(TokenMonitorValue(TokensMonitor.SCALE, lineConf))

                        else -> tmpStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Unhandled configuration of scale"))
                    }

                }

            }

        }

        if (extra) {
            extraStore.addAll(tmpStore)
            tmpStore.clear()
        } else {
            MonitorStore.add(tmpStore)
            tmpStore.clear()
        }
    }


    private fun disableMonitor(line: List<String>) {

        if (line[1] == "disable") {

            line.forEachIndexed { index, conf ->
                when (index) {
                    0 -> {
                        val tmpName = conf.ifBlank { "Automatic detection" }
                        tmpStore.add(TokenMonitorValue(TokensMonitor.NAME, tmpName))
                    }

                    1 -> tmpStore.add(TokenMonitorValue(TokensMonitor.RESOLUTION_DISABLE, conf))
                }
            }

        }

        MonitorStore.add(tmpStore)
        tmpStore.clear()
    }

    private fun reservedArea(conf: List<String>) {

        if (conf[1] == "addreserved") {

            conf.forEachIndexed { index, config ->
                when (index) {
                    0 -> {
                        val tmpName = config.ifBlank { "Automatic detection" }
                        tmpStore.add(TokenMonitorValue(TokensMonitor.NAME, tmpName))
                    }

                    1 -> tmpStore.add(TokenMonitorValue(TokensMonitor.RESOLUTION_RESERVED, config))

                    2 -> tmpStore.add(TokenMonitorValue(TokensMonitor.TOP, config.ifBlank { "0" }))

                    3 -> tmpStore.add(TokenMonitorValue(TokensMonitor.BOTTOM, config.ifBlank { "0" }))

                    4 -> tmpStore.add(TokenMonitorValue(TokensMonitor.LEFT, config.ifBlank { "0" }))

                    5 -> tmpStore.add(TokenMonitorValue(TokensMonitor.RIGHT, config.ifBlank { "0" }))
                }
            }

            MonitorStore.add(tmpStore)
            tmpStore.clear()

        } else {

            extraArgs(conf)
        }
    }


    private fun extraArgs(conf: List<String>) {

        val basicConf = conf.subList(0, 4)
        val extraArguments = conf.subList(4, conf.size)
        val size = extraArguments.size

        defaultConfig(basicConf, true)

        extraArguments.forEachIndexed { index, config ->

            when (config) {
                "mirror" -> {
                    if (index + 1 <= size) {
                        if (extraArguments[index + 1].isEmpty()) {
                            extraStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty args for mirror display"))
                        } else {
                            extraStore.add(TokenMonitorValue(TokensMonitor.EXTRA_MIRROR, extraArguments[index + 1]))
                        }
                    }
                }

                "bitdepth" -> {
                    if (index + 1 <= size) {
                        if (extraArguments[index + 1].isEmpty()) {
                            extraStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty args for mirror display"))
                        } else {
                            extraStore.add(TokenMonitorValue(TokensMonitor.EXTRA_BIT, extraArguments[index + 1]))
                        }
                    }
                }

                "transform" -> {
                    if (index + 1 <= size) {
                        if (extraArguments[index + 1].isEmpty()) {
                            extraStore.add(TokenMonitorValue(TokensMonitor.ERROR, "Empty args for mirror display"))
                        } else {
                            extraStore.add(TokenMonitorValue(TokensMonitor.EXTRA_TRANSFORMATION, extraArguments[index + 1]))
                        }
                    }
                }
            }
        }

        MonitorStore.add(extraStore)
        extraStore.clear()

    }

}