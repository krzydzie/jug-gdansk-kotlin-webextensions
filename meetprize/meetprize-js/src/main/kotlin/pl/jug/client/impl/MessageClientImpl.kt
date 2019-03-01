package pl.jug.client.impl

import pl.jug.client.*
import pl.jug.lib.ConnectionException
import pl.jug.lib.await
import runtime.MessageSender
import tabs.QueryInfo
import tabs.Tab
import webextensions.browser
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MessageClientImpl : MessageClient {
    override val consumer: MessageConsumer = MessageConsumerImpl()
    override val producer: MessageProducer = MessageProducerImpl()
}

class MessageConsumerImpl : MessageConsumer {
    var continuation: Continuation<Response>? = null

    init {
        val listener = { message: Response, sender: MessageSender, sendResponse: () -> Unit ->
            if (message.type == "response") {
                console.log("consumer - otrzymalem response nie external command=${message.command} value.size=${message.value.length}")
                continuation?.resume(message)
            }

            Unit
        }

        browser.runtime.onMessage.addListener(listener)
    }

    override suspend fun send(message: Request) = run {
        val activeTabId = getActiveTabId()

        if (activeTabId != null) {
            suspendCoroutine {
                continuation = it
                browser.tabs.sendMessage(activeTabId, message).catch { exc ->
                    continuation?.resumeWithException(ConnectionException(exc.message ?: "No error message"))
                }
            }
        } else {
            Response(MessageType.Attendees.name, "")
        }
    }

    private suspend fun getActiveTabId(): Int? {
        var activeTabId: Int? = null

        val activeTabArray =
            browser.tabs.query(QueryInfo(active = true, currentWindow = true)).await()

        val activeTab: Tab? = activeTabArray[0]

        if (activeTab == null) {
            console.log("brak active tab")
        } else {
            activeTabId = activeTab.id

            if (activeTabId == null) {
                console.log("tab is is null")
            }
        }

        return activeTabId
    }

}

class MessageProducerImpl : MessageProducer {
    override fun startServer(messageType: MessageType, responseHandler: (Request) -> Response) {
        val listener = { message: Request, sender: MessageSender, sendResponse: () -> Unit ->
            if (MessageType.valueOf(message.command) == messageType) {

                browser.runtime.sendMessage(message = responseHandler(message)).catch {
                    console.error("message = ${it.message}")
                }
            }

            Unit
        }

        browser.runtime.onMessage.addListener(listener)
    }

}
