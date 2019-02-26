package pl.jug.lib.impl

import org.w3c.dom.get
import pl.jug.environment.ExtensionPlace
import pl.jug.lib.Router
import kotlin.browser.window
import kotlin.reflect.KClass

class RouterImpl(override val routing: Map<ExtensionPlace, KClass<out Any>>) : Router {
    override fun getExtensionPlace() = window["extensionPlace"] as? String
}
