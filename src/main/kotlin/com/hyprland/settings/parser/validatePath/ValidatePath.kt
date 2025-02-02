package com.hyprland.settings.parser.validatePath

import com.hyprland.settings.parser.validatePath.exceptions.FileDoseNotExistException
import com.hyprland.settings.parser.validatePath.exceptions.FileIsNotReadableException
import com.hyprland.settings.parser.validatePath.exceptions.HyprlandSettingsFileDoseNotExist
import com.hyprland.settings.parser.validatePath.exceptions.HyprlandSettingsFileIsNotReadable
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path


/**
 *
 * ### [ValidatePath]
 *
 * ***Package*** [com.hyprland.settings.parser.validatePath]
 *
 * This class use for validating the default hyprland path and given the source path.
 *
 * ###### Methods
 *
 *-  [validateHyprlandPath] - Use for validating the default hyprland file path.
 *-  [validateSourceFileFromSettings] - Use for validation given the path of source files.
 *
 */
class ValidatePath {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    /**
     * ###### Hyprland settings default file path
     *
     * `TODO` This file need to be read from resource folder
     *
     */
    private val path = Path.of("${System.getProperty("user.home")}/.config/hypr/hyprland.conf")

    /**
     * ##### [validateHyprlandPath]
     *
     * Validates the default path to the Hyprland configuration file.
     *
     * The default file path is `/home/USERNAME/.config/hypr/hyprland.conf`.
     * This default can be changed, but the method
     * currently validates this default path.
     *
     * @return [Result]<[Path]>
     * 1. `onSuccess` this will return a valid path
     * 2. `onFailure` this will throw errors
     *
     * @throws HyprlandSettingsFileDoseNotExist
     * - If the file does not exist, recovery can be attempted by requesting a
     *   new default path.
     *
     * @throws HyprlandSettingsFileIsNotReadable
     * - If the file cannot be read, recovery can be attempted by asking the
     *   user to change the file's permissions.
     *
     * @throws Exception
     * - For all other cases, where the error is considered a bug or internal
     *   issue.
     */
    fun validateHyprlandPath(): Result<Path> = runCatching {

        defaultPathForHyprlandSettingExist(path)

        defaultPathForHyprlandSettingsReadable(path)

        return@runCatching path
    }


    /**
     * ###### Helper function for [validateHyprlandPath]
     *
     * This will validate if this file path exists.
     *
     * @param path File path for the hyprland settings file
     * @throws HyprlandSettingsFileDoseNotExist If a file dose doesn't exist
     */
    private fun defaultPathForHyprlandSettingExist(path: Path) {
        Files.exists(path)
            .takeUnless { it }
            ?.let {
                logger.error("Hyprland settings dose not exist in default path")
                throw HyprlandSettingsFileDoseNotExist(path.toString())
            }
    }


    /**
     * ###### Helper function for [validateHyprlandPath]
     *
     * This will try to read a file and check if it can be read.
     *
     * @param path File path for the hyprland settings file.
     * @throws HyprlandSettingsFileIsNotReadable If it couldn't read the file.
     */
    private fun defaultPathForHyprlandSettingsReadable(path: Path) = runCatching {

        // This tries to read the first line.
        BufferedReader(Files.newBufferedReader(path)).use { it.readLine() }
    }.onFailure {

        logger.error("Hyprland settings file dose exist but it not readable")
        throw HyprlandSettingsFileIsNotReadable(path.toString())
    }


    /**
     * ##### [validateSourceFileFromSettings]
     *
     * This try validate if a given file path is readable and exists.
     *
     * @param path File path given by source.
     * @return [Result]<[Path]> If successful return the validated path
     * @throws FileDoseNotExistException
     * 1. This exception will be thrown when the given file path doesn't exist
     *
     * @throws FileIsNotReadableException
     * 1. This exception will be thrown when the given file path doesn't exist
     */
    fun validateSourceFileFromSettings(path: Path): Result<Path> = runCatching {

        isSourceFileExist(path)

        isSourceFileReadable(path)

        return@runCatching path
    }


    /**
     * ###### Helper function for [validateSourceFileFromSettings]
     *
     * This will validate if the given source file path does exist.
     *
     * @param path Source file path
     * @throws FileDoseNotExistException When given file doesn't exist
     */
    private fun isSourceFileExist(path: Path) {

        Files.exists(path)
            .takeUnless { it }
            ?.let {

                logger.error("File dose not exist in this path => $path")
                throw FileDoseNotExistException(path.toString())
            }

    }


    /**
     * ###### Helper function for [validateSourceFileFromSettings]
     *
     * This will validate if the given file path can be read.
     *
     * @param path Source file path
     * @throws FileIsNotReadableException When given file could not be read.
     */
    private fun isSourceFileReadable(path: Path) = runCatching {

        BufferedReader(Files.newBufferedReader(path)).use { it.readLine() }
    }.onFailure {

        logger.error("This path is not readable => $path")
        throw FileIsNotReadableException(path.toString())
    }

}