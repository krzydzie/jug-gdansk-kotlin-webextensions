package pl.jug.model

data class MessageData(val text: String)

external interface ExternalMessageData {
    val text: String
}
