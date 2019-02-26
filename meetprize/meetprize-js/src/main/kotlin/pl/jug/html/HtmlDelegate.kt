package pl.jug.html

import pl.jug.lib.elementById
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class HtmlDelegate<T>(private val renderer: (T) -> String) : ReadWriteProperty<Any, T?> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        this.value = value
        property.elementById().html(evaluateHtml(value))
    }

    private fun evaluateHtml(value: T?): String = value?.let { renderer(value) } ?: ""
}
