package pl.jug.lib

import kotlinx.html.*
import kotlinx.html.js.button
import kotlinx.html.js.div
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.reflect.KProperty0

@HtmlTagMarker
fun TagConsumer<HTMLElement>.div(idField: KProperty0<*>, block: DIV.() -> Unit = {}): HTMLDivElement =
    div(id = idField.name, block = block)

@HtmlTagMarker
fun TagConsumer<HTMLElement>.div(id: String, block: DIV.() -> Unit = {}): HTMLDivElement = div {
    attributes["id"] = id
    block()
}


@HtmlTagMarker
fun <T, C : TagConsumer<T>> C.textArea(
    idField: KProperty0<*>,
    placeholder: String? = null,
    block: TEXTAREA.() -> Unit = {}
): T = textArea(id = idField.name, placeholder = placeholder, block = block)

@HtmlTagMarker
fun <T, C : TagConsumer<T>> C.textArea(id: String, placeholder: String? = null, block: TEXTAREA.() -> Unit = {}): T =
    textArea {
        attributes["id"] = id

        if (placeholder != null) {
            attributes["placeholder"] = placeholder
        }

        block()
    }

@HtmlTagMarker
fun TagConsumer<HTMLElement>.button(
    idField: KProperty0<*>,
    label: String,
    block: BUTTON.() -> Unit = {}
): HTMLButtonElement = button(id = idField.name, label = label, block = block)

@HtmlTagMarker
fun TagConsumer<HTMLElement>.button(id: String, label: String, block: BUTTON.() -> Unit = {}): HTMLButtonElement =
    button {
        attributes["id"] = id
        +label
        block()
    }

object lineSeparator {
    private const val RN = "\r\n"

    private val separator: String by lazy {
        val element = document.createElement("div")
        element.innerHTML = "<textarea>a\nb</textarea>"

        val rDetected = element.children[0]?.textContent?.contains("\r") ?: true
        if (rDetected) RN else "\n"
    }

    operator fun invoke() = separator
}