package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenExecute
import com.hyprland.settings.parser.filter.utils.TokenExecuteValue
import com.hyprland.settings.parser.store.ExecuteStore
import org.slf4j.LoggerFactory

/**
 *
 * ### [ExecuteTokens]
 *
 * Use for filtering and storing of execute keywords.
 *
 * ###### Methods
 *
 * [process] to filtering and storing.
 *
 */
class ExecuteTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    /**
     * Instant of [ExecuteStore]
     */
    private val executeStore = ExecuteStore

    /**
     * ##### [Process]
     *
     * This will store values according to keyword and values
     *
     * @param line as [String] line of value of `execute` settings
     * @param keyword as [String] keyword for find specific settings like `exec` ,`exec-once`
     */
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