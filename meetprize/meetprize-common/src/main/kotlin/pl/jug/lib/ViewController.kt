package pl.jug.lib

import kotlin.reflect.KClass

interface ViewController {
    val viewClass: KClass<out RenderedView>

    fun renderView() {
        lookupBean<RenderedView>(viewClass).render()
    }
}