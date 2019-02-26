package pl.jug.lib

import org.w3c.dom.Element
import pl.treksoft.jquery.JQuery
import pl.treksoft.jquery.jQuery
import kotlin.reflect.KProperty

fun jxQuery(selector: String) = jQuery(selector = selector, context = null as JQuery?)
fun jxQuery(selector: String, element: Element) = jQuery(selector = selector, context = element)
fun jxQuery(fieldId: KProperty<*>) = jxQuery("#${fieldId.name}")

fun KProperty<*>.elementById() = jxQuery(this)