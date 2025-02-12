package com.hyprland.settings.parser.loadHyprlandSettings.exceptions

/**
 *
 * ##### [HyprlandSettingsFileIsEmptyException]
 *
 * This will inform if a loaded hyprland file is empty.
 *
 */
class HyprlandSettingsFileIsEmptyException() : Exception("Loaded hyprland file is empty.")

/**
 *
 * ##### [HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException]
 *
 * This exception is thrown when after reading and filtering all source files and normal settings,
 * there is nothing to get back
 *
 * Likest to be a bug if this been thrown
 *
 */
class HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException() : Exception("Even after hyprland file is loaded it could not load it in to memory")