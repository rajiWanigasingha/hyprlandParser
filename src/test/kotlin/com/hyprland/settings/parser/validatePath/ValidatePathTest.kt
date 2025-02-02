package com.hyprland.settings.parser.validatePath

import com.hyprland.settings.parser.validatePath.exceptions.FileDoseNotExistException
import com.hyprland.settings.parser.validatePath.exceptions.FileIsNotReadableException
import com.hyprland.settings.parser.validatePath.exceptions.HyprlandSettingsFileDoseNotExist
import com.hyprland.settings.parser.validatePath.exceptions.HyprlandSettingsFileIsNotReadable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.platform.commons.logging.LoggerFactory
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.fail


class ValidatePathTest {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val validatePath = ValidatePath()
    private val homePath = System.getProperty("user.home")

    private val testPaths: List<String> = listOf(
        "$homePath/.config/hypr/conf/monitor.conf",
        "$homePath/.config/hypr/conf/environment.conf",
        "$homePath/IdeaProjects/testpaths/hellol",
        "$homePath/IdeaProjects/testpaths/world"
    )

    @Test
    fun `Check if default path to hyprland is valid`() {

        validatePath.validateHyprlandPath().fold(

            onSuccess = {

                assertEquals("${System.getProperty("user.home")}/.config/hypr/hyprland.conf", it.toString())
            },

            onFailure = { exception: Throwable ->

                when (exception) {

                    is HyprlandSettingsFileDoseNotExist -> {
                        logger.warn(exception) { exception.message }
                    }

                    is HyprlandSettingsFileIsNotReadable -> {
                        logger.warn(exception) { exception.message }
                    }

                    else -> {
                        logger.error(exception) { exception.message }

                        fail("Unexpected Error")
                    }
                }
            }

        )

    }


    @Test
    fun `Check if give file path is readable or exist`() {

        testPaths.forEach { testPaths: String ->

            validatePath.validateSourceFileFromSettings(path = Path.of(testPaths)).fold(

                onSuccess = {
                    assertEquals(testPaths, it.toString())
                },

                onFailure = { exception: Throwable ->
                    when (exception) {

                        is FileDoseNotExistException -> {
                            logger.warn(exception) { exception.message }
                        }

                        is FileIsNotReadableException -> {
                            logger.warn(exception) { exception.message }
                        }

                        else -> {
                            logger.error(exception) { exception.message }

                            fail()
                        }
                    }
                }
            )
        }
    }

}