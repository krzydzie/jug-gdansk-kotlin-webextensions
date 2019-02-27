import pl.jug.environment.impl.ConfigurationImpl
import pl.jug.lib.Application

fun main(args: Array<String>) {
    try {
        Application(ConfigurationImpl)
    } catch (e: Exception) {
        console.error(e.message)
    }
}

