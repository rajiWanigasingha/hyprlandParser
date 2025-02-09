package com.hyprland.settings.parser.filter.tokenzer

import com.hyprland.settings.parser.filter.utils.TokenBind
import com.hyprland.settings.parser.filter.utils.TokenBindValue
import com.hyprland.settings.parser.store.BindStore
import org.slf4j.LoggerFactory

class BindTokens {

    private val logger = LoggerFactory.getLogger(javaClass::class.java)
    private val tmpStore: MutableList<TokenBindValue> = mutableListOf()
    private val bindStore = BindStore

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

    private fun handleBindKeyword(conf: List<String>) {
        if (conf.size in 3..4) {

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

            bindStore.addToBind(tmpStore)
            tmpStore.clear()
        } else {

            bindStore.addToBind(
                listOf(
                    TokenBindValue(TokenBind.ERROR_KEY, conf.joinToString(" ,"))
                )
            )
        }
    }


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