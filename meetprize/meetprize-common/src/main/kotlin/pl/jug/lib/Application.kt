package pl.jug.lib

import pl.jug.environment.Configuration
import kotlin.reflect.KClass

class Application(private val configuration: Configuration) {
    companion object {
        lateinit var instance: Application
            private set
    }

    init {

        createBeanMappings().also { beanMappings ->
            BeanLocator.initialize(beanMappings)
        }

        configuration
            .router()
            .routToController().also {
                it.load()
            }

        instance = this
    }

    private fun createBeanMappings(): MutableMap<KClass<out Any>, Any> {
        Logger.setup(configuration)

        val repository: MutableMap<KClass<out Any>, Any> = mutableMapOf()

        repository += configuration.beanMappings.also {
            val message = if (it.isEmpty()) "Started but no beans found" else "${it.size} beans registered"
            logSystemMessage(message)
        }

        repository += JsLogger::class to configuration.jsLogger

        return repository
    }

    private fun logSystemMessage(message: String) {
        if (configuration.systemLogActive) {
            configuration.jsLogger.info("BeanRepository", message)
        }
    }
}