package demo.jug

import pl.jug.lib.async
import pl.jug.lib.await
import pl.jug.lib.jxQuery
import runtime.MessageSender
import tabs.QueryInfo
import tabs.Tab
import webextensions.browser

object SidebarPage {
    operator fun invoke() {
        console.log("hello world, sidebar page")
        addListener()
        async {
            jxQuery("#sendMessage").click {
                async {
                    sendMessage()
                    ""

                }
            }

        }
    }

    suspend fun sendMessage() {
        console.log("kliknieto przycisk")
        val activeTabArray =
            browser.tabs.query(QueryInfo(active = true, currentWindow = true)).await()
        val activeTab: Tab? = activeTabArray[0]
        if (activeTab == null) {
            console.log("brak active tab")
        } else {
            val id = activeTab.id

            if (id == null) {
                console.log("tab is is null")
            } else {
                console.log("wysylam komunikat")
                val ppp = browser.tabs.sendMessage(id, JugMessage("ping"))
                browser.tabs.sendMessage(id, JugMessage("ping")).await()
                console.log("komunikat wyslany")
            }

        }
    }

    fun addListener() {
        val listener = { message: JugMessageResponse, sender: MessageSender, sendResponse: () -> Unit ->
            console.log("side-bar  - otrzymalem message command=${message.command}")
        }

        browser.runtime.onMessage.addListener(listener)

    }
}