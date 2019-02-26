import pl.jug.lib.QUnit

object Settings {
    operator fun invoke() {
        try {
            QUnit.config.autostart = false
        } catch (e: dynamic) {
            console.error("failed QUnit.config.autostart = false", e)
        }
    }
}