package com.hyprland.settings.parser.filter

import com.hyprland.settings.parser.filter.exceptions.EmptySettingsLoadedToMemoryException
import com.hyprland.settings.parser.filter.tokenzer.BindTokens
import com.hyprland.settings.parser.filter.tokenzer.ExecuteTokens
import com.hyprland.settings.parser.filter.tokenzer.MonitorTokens
import com.hyprland.settings.parser.filter.tokenzer.VariableTokens
import com.hyprland.settings.parser.loadHyprlandSettings.result.LineObject
import org.slf4j.LoggerFactory

class Filter(private val settings: List<LineObject>) {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    private val matchExecutes = """^exec(?:r(?:-once)?|-shutdown|-once)?${'$'}""".toRegex()
    private val matchBinds = """^bind[lrtoe.nmtsdip]*${'$'}""".toRegex()
    private val matchVariables = """\$.+""".toRegex()

    private val filterForMonitor = MonitorTokens()
    private val filterForExecute = ExecuteTokens()
    private val filterForBind = BindTokens()
    private val filterForVariable = VariableTokens()

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