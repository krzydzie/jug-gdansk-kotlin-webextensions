import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.dom.append
import kotlinx.html.input
import kotlinx.html.js.div
import kotlinx.html.js.i
import kotlinx.html.js.onClickFunction
import kotlin.browser.document
import kotlin.browser.window

fun sample() {
    document.body!!.append {
        div(classes = "container") {
            input(type = InputType.text)

            button {
                + "Dodaj bookmark"
                onClickFunction = {
                    window.alert("Dodam bookmark")
                }
            }

            div(classes = "m-4")
            i { + "Miejsce na bookmark"}
        }
    }
}