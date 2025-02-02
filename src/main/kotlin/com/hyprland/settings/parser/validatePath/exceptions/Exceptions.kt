package com.hyprland.settings.parser.validatePath.exceptions

class HyprlandSettingsFileDoseNotExist(path: String) : Exception("Hyprland settings file dose not exist in default path => $path")

class HyprlandSettingsFileIsNotReadable(path: String) : Exception("Hyprland settings file dose exist but could not read. path => $path")

class FileDoseNotExistException(path: String) : Exception("Could not find any file is this path -> $path")

class FileIsNotReadableException(path: String) : Exception("Found a file but it not readable. path -> $path")