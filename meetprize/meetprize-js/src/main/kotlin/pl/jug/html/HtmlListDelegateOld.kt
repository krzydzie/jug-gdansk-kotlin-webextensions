package pl.jug.html

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class HtmlListDelegateOld<T>(private val renderer: (T) -> String) : ReadOnlyProperty<Any, HtmlListElement<T>> {
    private lateinit var htmlListElement: HtmlListElement<T>

    override fun getValue(thisRef: Any, property: KProperty<*>): HtmlListElement<T> {
        if (!::htmlListElement.isInitialized) {
            htmlListElement = HtmlListElement(property.name, renderer)
        }

        return htmlListElement
    }

}
