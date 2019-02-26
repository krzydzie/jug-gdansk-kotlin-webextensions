import kotlinx.html.InputType
import kotlinx.html.dom.append
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document

object Tags {
    lateinit var input: HTMLInputElement
    lateinit var span: HTMLSpanElement
}

fun buildHtml() {
    document.body!!.append {
        div(classes = "container") {
            Tags.input = input(type = InputType.text) {
                attributes["value"] = "google"
            }

            button {
                +"Dodaj bookmark"

                onClickFunction = {
                    Tags.input.value.also { text ->
                        Tags.span.innerHTML = text
                        //bookmarkPageInFolder(toolbarBookmarkId, "Ulubione", text)
                    }
                }
            }

            div(classes = "m-4")

            i { +"Nowy bookmark: " }

            Tags.span = span()

        }
    }
}

