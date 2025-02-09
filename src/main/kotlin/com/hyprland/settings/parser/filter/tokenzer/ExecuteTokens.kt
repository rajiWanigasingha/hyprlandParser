package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenExecute
import com.hyprland.settings.parser.filter.utils.TokenExecuteValue
import com.hyprland.settings.parser.store.ExecuteStore
import org.slf4j.LoggerFactory

class ExecuteTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val executeStore = ExecuteStore

    fun process(line: String, keyword: String) {

        line
            .trim()
            .substringBefore("#")
            .let {

                when (keyword) {
                    in setOf(
                        "exec-once",
                        "execr-once"
                    ) -> executeStore.addToExec(TokenExecuteValue(TokenExecute.EXECUTE_ONCE, it))
                    in setOf("exec", "execr") -> executeStore.addToExec(
                        TokenExecuteValue(
                            TokenExecute.EXECUTE,
                            it
                        )
                    )
                    "exec-shutdown" -> executeStore.addToExec(
                        TokenExecuteValue(
                            TokenExecute.EXECUTE_SHUTDOWN,
                            it
                        )
                    )
                }

            }

    }

}