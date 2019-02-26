package pl.jug.client

data class Request(var command: String = "", var value: String = "", val type: String = "request")