package pl.jug.client

interface MessageClient {
    val consumer: MessageConsumer
    val producer: MessageProducer

}

interface MessageConsumer {
    suspend fun send(message: Request): Response
}

interface MessageProducer {
    fun startServer(messageType: MessageType, responseResolver: (Request) -> Response)

}