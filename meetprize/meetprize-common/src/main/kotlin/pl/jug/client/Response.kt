package pl.jug.client

data class Response(var command: String = "", var value: String = "", val type: String = "response")