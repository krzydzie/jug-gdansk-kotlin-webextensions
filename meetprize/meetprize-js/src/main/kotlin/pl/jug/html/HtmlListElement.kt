package pl.jug.html

import pl.jug.lib.jxQuery

class HtmlListElement<T>(private val id: String, private val renderer: (T) -> String) {
    private val list = mutableListOf<T>()

    operator fun plusAssign(item: T) {
        getElement().append("</br>${renderer(item)}")

        list += item
    }

    fun reset() {
        getElement().html("")
    }

    fun getList(): List<T> = list

    private fun getElement() = jxQuery("#$id")

}
