package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenBind
import com.hyprland.settings.parser.filter.utils.TokenBindValue
import com.hyprland.settings.parser.store.BindStore
import org.slf4j.LoggerFactory

/**
 *
 * ### [BindTokens]
 *
 * This use for further filtering for `binds` and store them in [BindStore]
 *
 * ###### Method
 *
 * [process] This use for filter and store
 *
 */
class BindTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    /**
     * Create a temporary store to hold all similar values
     */
    private val tmpStore: MutableList<TokenBindValue> = mutableListOf()

    /**
     * Get access to key bind store [BindStore]
     */
    private val bindStore = BindStore

    /**
     *
     * #### [Process]
     *
     * This use for filtering and storing binds
     *
     * @param line as [String] line of bindings
     * @param keyword as [String] keyword for check for special args like `alrd`
     */
    fun process(line: String, keyword: String) {

        line
            .trim()
            .substringBefore("#")
            .split(",")
            .let {
                if (keyword == "bind") {
                    handleBindKeyword(it)
                } else {
                    handleExtendedBindKeyword(it ,keyword)
                }
            }

    }

    /**
     *
     * ##### [handleBindKeyword]
     *
     * Helper method for [Process].use to process Keywords without any special args
     *
     * @param conf as [List]<[String]> for all keybindings
     */
    private fun handleBindKeyword(conf: List<String>) {
        if (conf.size in 3..4) {

            /**
             * goes through all keywords and store them accordingly in tmpstore.
             */
            conf.forEachIndexed { index: Int, bind: String ->

                when (index) {

                    0 -> tmpStore.add(TokenBindValue(TokenBind.MODS, bind.ifEmpty { "No `Mod` bind key." }))

                    1 -> {

                        if (bind.isEmpty() && conf[0].isEmpty()) {
                            tmpStore.add(TokenBindValue(TokenBind.ERROR, "Both Mod and Key are empty"))
                            return@forEachIndexed
                        }

                        tmpStore.add(TokenBindValue(TokenBind.KEY, bind.ifEmpty { "No `Key` bind key" }))
                    }

                    2 -> {

                        if (bind.isEmpty()) {
                            tmpStore.add(TokenBindValue(TokenBind.ERROR, "Dispatcher is empty"))
                            return@forEachIndexed
                        }

                        tmpStore.add(TokenBindValue(TokenBind.DISPATCHER, bind))
                    }


                    3 -> tmpStore.add(TokenBindValue(TokenBind.PARAMS, bind))

                }

            }

            /**
             * Store in [BindStore] and clear anything in [tmpStore]
             */
            bindStore.addToBind(tmpStore)
            tmpStore.clear()
        } else {

            /**
             * If invalid amount of keybindings
             */
            bindStore.addToBind(
                listOf(
                    TokenBindValue(TokenBind.ERROR_KEY, conf.joinToString(" ,"))
                )
            )
        }
    }


    /**
     *
     * ###### [handleExtendedBindKeyword]
     *
     * This use if there are any separate keywords
     *
     * @param conf as [List]<[String]>
     * @param keyword as [String]
     */
    private fun handleExtendedBindKeyword(conf: List<String>, keyword: String) {

        val argsBind = keyword.substringAfter("bind")

//        argsBind.forEach { arg: Char ->
//
//            if (arg == 'd') {
//                if (conf.size in 3..5 && conf[2] !in setOf("!" ,",")) {
//
//                }
//            }
//
//        }

    }

}