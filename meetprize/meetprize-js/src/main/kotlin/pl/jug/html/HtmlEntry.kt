package pl.jug.html

import pl.jug.lib.jxQuery
import pl.treksoft.jquery.JQuery

class HtmlEntry(private val containerId: String) : DomEntry {
    private val element: JQuery
        get() = jxQuery("#$containerId")

    override fun appendToContainer(html: String) {
        element.append(html)
    }

    override fun appendNewLineToContainer() {
        appendToContainer("</br>")
    }

    override fun cleanContainer() {
        element.html("")
    }
}