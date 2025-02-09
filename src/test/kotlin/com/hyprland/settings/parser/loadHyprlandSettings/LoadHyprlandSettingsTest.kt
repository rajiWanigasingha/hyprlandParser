package com.hyprland.settings.parser.loadHyprlandSettings

import com.hyprland.settings.parser.loadHyprlandSettings.result.LineObject
import com.hyprland.settings.parser.loadHyprlandSettings.result.Type
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class LoadHyprlandSettingsTest {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    private val loadHyprlandSettings = LoadHyprlandSettings()

    @Test
    fun `Test if it load all the settings from hyprland`() {

        runCatching {

            val loadedSettings = loadHyprlandSettings.load()

            loadedSettings.fold(

                onSuccess = {

                    if (it.metaData.type == Type.OK) {
                        it.body?.forEach { lineObject: LineObject ->
                            logger.info("${lineObject.message} ${lineObject.line}")
                        }
                    } else {

                        logger.warn("${it.metaData.statusCode} -> ${it.metaData.errorTags} : ${it.metaData.message}")
                    }

                },

                onFailure = { exception: Throwable ->
                    logger.warn(exception.message, exception)
                }

            )

        }.onFailure { exception: Throwable ->

            logger.error(exception.message, exception)
        }

    }

}