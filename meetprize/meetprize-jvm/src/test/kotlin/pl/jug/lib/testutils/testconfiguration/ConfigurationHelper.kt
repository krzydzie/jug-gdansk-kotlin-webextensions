package pl.jug.lib.testutils.testconfiguration

import pl.jug.environment.Configuration
import pl.jug.environment.ExtensionPlace
import pl.jug.lib.JsLogger
import pl.jug.lib.LogLevel
import pl.jug.lib.testutils.testdata.*

class ConfigurationHelper {
    fun forLoggerTest(jsLogger: JsLogger) = object : Configuration {
        override val jsLogger = jsLogger
        override val defaultLogLevel = LogLevel.DEBUG
        override val classLogLevel: Map<String, LogLevel> = mapOf("TestClassWithInfoLevel" to LogLevel.INFO)
        override val systemLogActive = true
        override val routing = mapOf(ExtensionPlace.Content to TestPage::class)
        override val router = { TestRouter(routing) }
        override val beanMappings = mapOf(
            TestPage::class to TestPage(),
            TestClass::class to TestClass(),
            TestClassWithInfoLevel::class to TestClassWithInfoLevel()
        )
    }

    fun forConfigurationTest() = object : Configuration {
        override val jsLogger = TestJsLogger()
        override val defaultLogLevel = LogLevel.DEBUG
        override val classLogLevel: Map<String, LogLevel> = mapOf("TestClassWithInfoLevel" to LogLevel.INFO)
        override val systemLogActive = false
        override val routing = mapOf(ExtensionPlace.Content to TestPage::class)
        override val router = { TestRouter(routing) }
        override val beanMappings = mapOf(
            TestPage::class to TestPage(),
            TestClass::class to TestClass(),
            TestClassWithInfoLevel::class to TestClassWithInfoLevel()
        )
    }

    fun forApplicationTest() = object : Configuration {
        override val jsLogger = TestJsLogger()
        override val defaultLogLevel = LogLevel.DEBUG
        override val classLogLevel: Map<String, LogLevel> = mapOf("TestClassWithInfoLevel" to LogLevel.INFO)
        override val systemLogActive = true
        override val routing = mapOf(ExtensionPlace.Content to TestPage::class)
        override val router = { TestRouter(routing) }
        override val beanMappings = mapOf(
            TestPage::class to TestPage(),
            TestClass::class to TestClass(),
            TestClassWithInfoLevel::class to TestClassWithInfoLevel()
        )
    }


}