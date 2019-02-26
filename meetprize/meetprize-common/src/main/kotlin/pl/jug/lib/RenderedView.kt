package pl.jug.lib

abstract class RenderedView {
    private var rendered: Boolean = false

    fun renderView() {
        if (!rendered) {
            render()
            rendered = true
        }
    }

    abstract fun render()

}