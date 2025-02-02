package com.hyprland.settings.parser.loadHyprlandSettings.exceptions

class HyprlandSettingsFileIsEmptyException() : Exception("Loaded hyprland file is empty.")

class HyprlandSettingsFileIsEmptyAfterSettingsFileLoadedException() : Exception("Even after hyprland file is loaded it could not load it in to memory")