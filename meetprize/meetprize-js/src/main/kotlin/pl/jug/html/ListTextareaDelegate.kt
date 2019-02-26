package pl.jug.html

import pl.jug.lib.elementById
import pl.jug.view.impl.toHtml
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ListTextareaDelegate : ReadWriteProperty<Any, List<String>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): List<String> =
        (property.elementById().`val`() as? String ?: "").lines()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<String>) {
        property.elementById().`val`(value.toHtml())
    }

}

