package pl.jug.environment

import pl.jug.lib.JsLogger
import pl.jug.lib.LogLevel
import pl.jug.lib.Router
import kotlin.reflect.KClass

interface Configuration {
    val jsLogger: JsLogger
    val defaultLogLevel: LogLevel
    val classLogLevel: Map<String, LogLevel>
    val systemLogActive: Boolean
    val routing: Map<ExtensionPlace, KClass<out Any>>
    val beanMappings: Map<KClass<out Any>, Any>
    val router: () -> Router


}