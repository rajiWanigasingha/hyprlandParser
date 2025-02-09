package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenVariablesValue
import com.hyprland.settings.parser.store.VariableStore
import org.slf4j.LoggerFactory

class VariableTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val variableStore = VariableStore

    fun process(line: String, keyword: String) {

        line
            .trim()
            .substringBefore("#")
            .let {
                handleVariable(keyword, it)
            }

    }

    private fun handleVariable(varName: String, varValue: String) {

        variableStore.addToVariable(
            TokenVariablesValue(
                value = varValue,
                name = varName
            )
        )
    }

}