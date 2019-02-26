package pl.jug.lib

import pl.jug.environment.ExtensionPlace
import kotlin.reflect.KClass

interface Router {
    val routing: Map<ExtensionPlace, KClass<out Any>>
    fun getExtensionPlace(): String?

    fun routToController(): PageController {
        val place = getExtensionPlace() ?: throw IllegalStateException("ExtensionPlace not set")
        val extensionPlace = ExtensionPlace.valueOfPlace(place)
        val beanClass = routing[extensionPlace] ?: throw IllegalStateException("$extensionPlace not routed")

        return lookupBean(beanClass)
    }
}
