package demo.jug


external interface JugMessageResponse {
    val command: String
}

data class JugMessage(val command: String)