package pl.jug.lib

import pl.jug.environment.Configuration

class Logger(private val className: String) {

    companion object {
        private lateinit var jsLogger: JsLogger
        private lateinit var defaultLogLevel: LogLevel
        private lateinit var classLogLevel: Map<String, LogLevel>

        inline fun <reified T> create() = Logger(T::class.simpleName ?: "root")

        fun setup(configuration: Configuration) {
            jsLogger = configuration.jsLogger
            defaultLogLevel = configuration.defaultLogLevel
            classLogLevel = configuration.classLogLevel
        }
    }

    fun trace(message: String) {
        jsLogger.takeIf { isLevelActive(LogLevel.TRACE) }?.debug(className, message)
    }

    fun debug(message: String) {
        jsLogger.takeIf { isLevelActive(LogLevel.DEBUG) }?.debug(className, message)
    }

    fun info(message: String) {
        jsLogger.takeIf { isLevelActive(LogLevel.INFO) }?.info(className, message)
    }

    fun error(message: String) {
        jsLogger.error(className, message)
    }

    fun dir(obj: Any, message: String? = null) {
        val dirTitle = if (message != null) "$className: $message" else "$className: dir object"
        jsLogger.takeIf { isLevelActive(LogLevel.INFO) }?.dir(dirTitle, obj)
    }

    fun dirDebug(obj: Any, message: String? = null) {
        val dirTitle = if (message != null) "$className: $message" else "$className: dir object"
        jsLogger.takeIf { isLevelActive(LogLevel.DEBUG) }?.dir(dirTitle, obj)
    }

    private fun isLevelActive(logLevel: LogLevel) = run {
        val logLevelToMatch = classLogLevel[className] ?: defaultLogLevel
        logLevel >= logLevelToMatch
    }

}