package demo

import pl.jug.lib.jxQuery
import pl.treksoft.jquery.jQuery

object HtmlDemo {
    fun sample() {
        jQuery().ready {
            try {
                console.log("inputText = " + jxQuery("#inputText").length)
                val text = jxQuery("#inputText").`val`()
                jxQuery("#showDiv").html("w showDiv wartosc text:$text")

                jxQuery("#actionButton").click {
                    val text = jxQuery("#inputText").`val`()
                    jxQuery("#showDiv").html("kliknieto przycisk, text=$text")
                }

            } catch (e: Exception) {
                console.log("exception")
                console.dir(e)
            }
        }

    }
}