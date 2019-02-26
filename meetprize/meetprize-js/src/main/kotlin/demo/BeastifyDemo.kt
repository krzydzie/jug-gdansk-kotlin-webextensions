package demo

import demo.beastify.addBeastMessageListener
import demo.beastify.injesctTabScripts
import demo.beastify.onClickBest
import demo.beastify.onClickReset
import pl.jug.lib.async
import pl.jug.lib.jxQuery

object BeastifyDemo {
    operator fun invoke() {
        val place = getExtensionPlace() ?: throw RuntimeException("extensionPlace not set")
        async {
            console.log("BeastifyDemo, place=$place")

            when (place) {
                "browser_action" -> {
                    injesctTabScripts()


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

fun getExtensionPlace(): String? = js("window.extensionPlace") as? String

object extensionHasRun {
    val value: Boolean get() {
        val result = (js("window.extensionHasRun") as? String) != null

        if (!result) {
            js("window.extensionHasRun = 'true'")
        }

        return result
    }
}

val hidePage = "body > :not(.beastify-image) {display: none;}"

data class BeastifyMessage(val command: String, val beastURL: String)

external interface BeastifyMessageResponse{
    val command: String
    val beastURL: String
}
