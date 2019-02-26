package pl.jug.lib.testutils.testdata

import pl.jug.environment.ExtensionPlace
import pl.jug.lib.Router
import kotlin.reflect.KClass

class TestRouter(override val routing: Map<ExtensionPlace, KClass<out Any>>) : Router {
    var nullPlace: Boolean = false
    var unknownPlace: Boolean = false

    override fun getExtensionPlace() = when {
        nullPlace -> null
        unknownPlace -> "abc"
        else -> ExtensionPlace.Content.name
    }
}

fun Router.configureTestRouter(nullPlace: Boolean = false, unknownPlace: Boolean = false) {
    this as TestRouter
    this.nullPlace = nullPlace
    this.unknownPlace = unknownPlace
}