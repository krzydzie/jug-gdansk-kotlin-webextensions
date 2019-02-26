package pl.jug.html

class ListElement<T>(
    private val id: String,
    private val renderer: (T) -> String,
    domEntry: DomEntry
) : DomEntry by domEntry {

    private val internalList = mutableListOf<T>()

    val list: List<T>
        get() = internalList

    operator fun plusAssign(item: T) {
        appendNewLineToContainer()
        appendToContainer(renderer(item))
        internalList += item
    }

    fun clean() {
        cleanContainer()
    }
}