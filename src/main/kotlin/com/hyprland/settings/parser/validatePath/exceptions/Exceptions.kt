package com.hyprland.settings.parser.validatePath.exceptions


/**
 * ##### [HyprlandSettingsFileDoseNotExist]
 *
 * This will be thrown when hyprland file does not exist in the default
 * path.
 *
 * default path = `/home/USER/.config/hypr/hyprland.conf`
 *
 * This exception can be recovered by changing the default path.
 */
class HyprlandSettingsFileDoseNotExist(path: String) :
    Exception("Hyprland settings file dose not exist in default path => $path")

/**
 * ##### [HyprlandSettingsFileIsNotReadable]
 *
 * This will be thrown when hyprland file could not be read.
 *
 * most likely a permission error
 */
class HyprlandSettingsFileIsNotReadable(path: String) :
    Exception("Hyprland settings file dose exist but could not read. path => $path")

/**
 * ##### [FileDoseNotExistException]
 *
 * This will be used for loading source files that exist on hyprland
 * settings file. If that path does not exist, this will be thrown.
 *
 * @param path Type [String], Invalid path for that source file
 */
class FileDoseNotExistException(path: String) : Exception("Could not find any file is this path -> $path")

/**
 * ##### [FileIsNotReadableException]
 *
 * This will be used for loading source files that exist on hyprland
 * settings file. If that path could not be read, this will be thrown.
 *
 * @param path Type [String], Invalid path for that source file
 */
class FileIsNotReadableException(path: String) : Exception("Found a file but it not readable. path -> $path")

/**
 * ##### [NoPathInResourceFolder]
 *
 * This will throw if there is nothing in `hyprLock.json` file.
 *
 * it contains the path to your hyprland file, after home.
 */
class NoPathInResourceFolder() : Exception("No Path Found In resource folder.")