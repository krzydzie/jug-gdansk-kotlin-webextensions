package pl.jug.lib.testutils.testdata

import pl.jug.lib.JsLogger

class TestJsLogger : JsLogger {
    val logs: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun trace(prefix: String, vararg o: Any?) {
        addLog("trace", prefix, o)
    }

    override fun info(prefix: String, vararg o: Any?) {
        addLog("info", prefix, o)
    }

    override fun debug(prefix: String, vararg o: Any?) {
        addLog("debug", prefix, o)
    }

    override fun error(prefix: String, vararg o: Any?) {
        addLog("error", prefix, o)
    }

    override fun dir(prefix: String, obj: Any) {
        addLog("dir", prefix, emptyArray())
    }

    private fun addLog(type: String, prefix: String, arg: Array<out Any?>) {
        val message = if (arg.isEmpty()) "" else arg[0]
        logs.putIfAbsent(type, mutableListOf())
        val list = logs[type] ?: throw RuntimeException("Unexpected null list")
        list += "$prefix ${message.nullToEmptyString()}"
    }
}

fun Any?.nullToEmptyString() = this?.toString() ?: ""