package demo

//import lib.QUnit
import pl.jug.lib.jxQuery
import pl.treksoft.jquery.jQuery

//import kotlin.browser.window

object BorderifyDemo {

    operator fun invoke() {
        try {
            jQuery().ready {
                jxQuery("body").css("border", "5px solid blue")
            }
        } catch (e: dynamic) {
            console.error("failed body css", e)
        }

    }
}