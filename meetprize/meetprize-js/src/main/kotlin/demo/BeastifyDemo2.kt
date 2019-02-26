package demo

import demo.beastify.addBeastMessageListener
import demo.beastify.onClickBest
import demo.beastify.onClickReset
import pl.jug.lib.async
import pl.jug.lib.jxQuery

object BeastifyDemo2 {
    operator fun invoke() {
        val place = getExtensionPlace() ?: throw RuntimeException("extensionPlace not set")
        async {
            console.log("BeastifyDemo2, place=$place")

            when (place) {
                "browser_action" -> {
                    jxQuery("div.button.beast").click {
                        async {
                            onClickBest(it)
                        }
                    }

                    jxQuery("div.button.reset").click {
                        async {
                            onClickReset()
                        }
                    }

                }

                "tab_script" -> {
                    console.log("tab script started")

                    if (!extensionHasRun.value) {
                        addBeastMessageListener()
                    }
                    console.log("end of tab_script")

                    ""


                }

                else -> throw RuntimeException("unknown extension place")
            }
        }.catch {
            console.error("zlapal wyjatek")
            console.dir(it)
        }

    }

}