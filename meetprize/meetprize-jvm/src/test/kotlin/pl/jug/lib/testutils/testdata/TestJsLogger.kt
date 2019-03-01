package pl.jug.lib.testutils.testdata

import pl.jug.lib.JsLogger

class TestJsLogger : JsLogger {
    val logs: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun trace(prefix: String, message: String) {
        addLog("trace", prefix, message)
    }

    override fun info(prefix: String, message: String) {
        addLog("info", prefix, message)
    }

    override fun debug(prefix: String, message: String) {
        addLog("debug", prefix, message)
    }

    override fun error(prefix: String, message: String) {
        addLog("error", prefix, message)
    }

    override fun dir(prefix: String, obj: Any) {
        addLog("dir", prefix, "")
    }

    private fun addLog(type: String, prefix: String, message: String) {
        logs.putIfAbsent(type, mutableListOf())
        val list = logs[type] ?: throw RuntimeException("Unexpected null list")
        list += "$prefix ${message.nullToEmptyString()}"
    }
}

fun Any?.nullToEmptyString() = this?.toString() ?: ""