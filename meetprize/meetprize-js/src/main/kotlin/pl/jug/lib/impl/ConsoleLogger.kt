package pl.jug.lib.impl

import pl.jug.lib.JsLogger

class ConsoleLogger : JsLogger {
    override fun trace(prefix: String, message: String) {
        console.info("TRACE $prefix: $message")
    }

    override fun debug(prefix: String, message: String) {
        console.info("DEBUG $prefix: $message")
    }

    override fun info(prefix: String, message: String) {
        console.log("$prefix: $message")
    }

    override fun error(prefix: String, message: String) {
        console.error("$prefix: $message")
    }

    override fun dir(prefix: String, obj: Any) {
        with(console) {
            info(prefix)
            dir(obj)
        }
    }

    private fun buildMessage(prefix: String, params: Array<out Any?>): String {
//        val joinedParams = params.takeIf { params.isNotEmpty() }?.let { it.joinToString(separator = " ", prefix = ": ") } ?: ""
        val joinedParams = if (params.isNotEmpty()) ": ${params[0]}" else ""
        return "$prefix$joinedParams"
    }
}