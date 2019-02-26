package pl.jug.html

interface DomEntry {
    fun appendToContainer(html: String)
    fun appendNewLineToContainer()
    fun cleanContainer()
}