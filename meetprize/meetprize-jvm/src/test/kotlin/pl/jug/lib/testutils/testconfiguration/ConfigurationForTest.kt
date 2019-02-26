package pl.jug.lib.testutils.testconfiguration

import pl.jug.environment.Configuration
import pl.jug.environment.ExtensionPlace
import pl.jug.lib.JsLogger
import pl.jug.lib.LogLevel
import pl.jug.lib.Router
import pl.jug.lib.testutils.testdata.TestJsLogger
import pl.jug.lib.testutils.testdata.TestRouter
import kotlin.reflect.KClass

class ConfigurationForTest(
    routing: Map<ExtensionPlace, KClass<out Any>>,
    jsLogger: JsLogger = TestJsLogger(),
    defaultLogLevel: LogLevel = LogLevel.DEBUG,
    classLogLevel: Map<String, LogLevel> = emptyMap(),
    systemLogActive: Boolean = true,
    routerHandler: () -> Router = { TestRouter(routing) },
    beanMappings: Map<KClass<out Any>, Any>
) : Configuration {
    override val jsLogger = jsLogger
    override val defaultLogLevel = defaultLogLevel
    override val classLogLevel = classLogLevel
    override val systemLogActive = systemLogActive
    override val routing = routing
    override val router = routerHandler
    override val beanMappings = beanMappings
}
