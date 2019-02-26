package pl.jug.html

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class HtmlListDelegate<T>(private val renderer: (T) -> String) : ReadOnlyProperty<Any, ListElement<T>> {
    private lateinit var listElement: ListElement<T>

    override fun getValue(thisRef: Any, property: KProperty<*>): ListElement<T> {
        if (!::listElement.isInitialized) {
            listElement = ListElement(property.name, renderer, HtmlEntry(property.name))
        }

        return listElement
    }

}
