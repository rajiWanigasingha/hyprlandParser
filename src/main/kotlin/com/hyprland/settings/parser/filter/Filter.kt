package com.hyprland.settings.parser.filter

import com.hyprland.settings.parser.filter.exceptions.EmptySettingsLoadedToMemoryException
import com.hyprland.settings.parser.filter.tokenzer.BindTokens
import com.hyprland.settings.parser.filter.tokenzer.ExecuteTokens
import com.hyprland.settings.parser.filter.tokenzer.MonitorTokens
import com.hyprland.settings.parser.filter.tokenzer.VariableTokens
import com.hyprland.settings.parser.loadHyprlandSettings.result.LineObject
import org.slf4j.LoggerFactory

/**
 *
 * ### [Filter]
 *
 * This includes methods to filter and store record form loaded hyprland file.
 *
 * ##### Methods
 *
 * [sortAndFilter], This will filter and store records in stores.
 *
 * @param settings Type as [List]<[LineObject]>
 *
 */
class Filter(private val settings: List<LineObject>) {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    /**
     * Regex for a match pattern for `execute` hyprland settings
     */
    private val matchExecutes = """^exec(?:r(?:-once)?|-shutdown|-once)?${'$'}""".toRegex()

    /**
     * Regex for the match pattern for `binds` hyprland settings
     */
    private val matchBinds = """^bind[lrtoe.nmtsdip]*${'$'}""".toRegex()

    /**
     *  Regex for the match pattern for `variables` hyprland settings
     */
    private val matchVariables = """\$.+""".toRegex()

    /**
     * create an instance of [MonitorTokens]
     */
    private val filterForMonitor = MonitorTokens()
    /**
     * create an instance of [ExecuteTokens]
     */
    private val filterForExecute = ExecuteTokens()
    /**
     * create an instance of [BindTokens]
     */
    private val filterForBind = BindTokens()
    /**
     * create an instance of [VariableTokens]
     */
    private val filterForVariable = VariableTokens()


    /**
     *
     * #### [sortAndFilter]
     *
     * This will take [settings] from class and filter all settings and store in specific stores.
     *
     * @throws EmptySettingsLoadedToMemoryException If [settings] is empty.
     */
    fun sortAndFilter() = runCatching {

        settings.takeIf { it.isEmpty() }?.let { throw EmptySettingsLoadedToMemoryException() }

        settings.map { lineObject: LineObject ->
            lineObject.error
                .takeIf { !it }
                ?.let {
                    lineObject.message?.let { message -> sortIntoKeywords(message, lineObject.line) }
                }
                ?: logger.warn(lineObject.message)
        }

    }


    /**
     *
     * ###### [sortIntoKeywords]
     *
     * Helper method for [sortAndFilter].This will check what kind of settings
     * it is a call required method to further filter and store
     *
     * @param message as Type [String]
     * @param line as Type [String]
     *
     */
    private fun sortIntoKeywords(message: String, line: String) {

        when {
            message == "monitor" -> filterForMonitor.process(line)

//            message == "workspace" -> logger.info(line)

            matchExecutes.matches(message) -> filterForExecute.process(line, message)

//            message in setOf("windowrule" ,"windowrulev2") -> logger.info(line)

            matchBinds.matches(message) -> filterForBind.process(line, message)

            matchVariables.matches(message) -> filterForVariable.process(line, message)
        }

    }

}