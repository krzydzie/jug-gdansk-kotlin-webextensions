package pl.jug.lib

interface JsLogger {
    fun trace(prefix: String, message: String)
    fun info(prefix: String, message: String)
    fun debug(prefix: String, message: String)
    fun error(prefix: String, message: String)
    fun dir(prefix: String, obj: Any)
}