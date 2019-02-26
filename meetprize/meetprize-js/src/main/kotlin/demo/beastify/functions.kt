package demo.beastify

import demo.BeastifyMessage
import demo.BeastifyMessageResponse
import demo.hidePage
import extensionTypes.InjectDetails
import pl.jug.lib.await
import pl.jug.lib.jxQuery
import pl.treksoft.jquery.JQueryEventObject
import runtime.MessageSender
import tabs.QueryInfo
import tabs.Tab
import webextensions.browser

suspend fun injesctTabScripts() {
    val extensionPlace =
        browser.tabs.executeScript(details = InjectDetails(code = "window.extensionPlace")).await()

    if (extensionPlace?.get(0) == null) {
        console.log("inject started")
        browser.tabs.executeScript(details = InjectDetails(code = "window.extensionPlace = 'tab_script'"))
            .await()
        browser.tabs.executeScript(details = InjectDetails(file = "/meetupprize-js/jquery-3.3.1.slim.js"))
            .await()
        browser.tabs.executeScript(details = InjectDetails(file = "/meetupprize-js/build/kotlin-js-min/main/kotlin.js"))
            .await()
        browser.tabs.executeScript(details = InjectDetails(file = "/meetupprize-js/build/kotlin-js-min/main/declarations.js"))
            .await()
        browser.tabs.executeScript(details = InjectDetails(file = "/meetupprize-js/build/kotlin-js-min/main/meetupprize-js.js"))
            .await()
        console.log("inject finished")
    } else {
        console.log("script allready installed")
    }
}

suspend fun onClickBest(it: JQueryEventObject): String {
    val beastValue = (it.target.textContent ?: "").toLowerCase()
    console.log("klikniety div $beastValue")
    val beastUrl = browser.runtime.getURL("images/beasts/$beastValue.jpg")
    console.log("beastUrl = $beastUrl")
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
            browser.tabs.insertCSS(details = InjectDetails(code = hidePage)).await()
            browser.tabs.sendMessage(id, BeastifyMessage("beastify", beastUrl)).await()
            console.log("message wyslany")
        }
    }
    return ""
}


suspend fun onClickReset() {
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
            browser.tabs.removeCSS(details = InjectDetails(code = hidePage)).await()
            browser.tabs.sendMessage(id, BeastifyMessage("reset", "")).await()
            console.log("message reset wyslany")
        }
    }
}


fun addBeastMessageListener() {
    console.log("TAB - adding listener ...")
    val listener = { message: BeastifyMessageResponse, sender: MessageSender, sendResponse: () -> Unit ->
        val beastURL = message.beastURL
        console.log("otrzymalem message command=${message.command}, beastURL=$beastURL")
        when (message.command) {
            "beastify" -> {
                jxQuery(".beastify-image").remove()
                jxQuery("body").append("<img src='$beastURL' style='height:100vh' class='beastify-image'>")
                console.log("koniec")
            }

            "reset" -> {
                jxQuery(".beastify-image").remove()
                console.log("rest")
            }

            else -> {
                console.log("nieznany beas")
            }
        }

    }

    browser.runtime.onMessage.addListener(listener)
    console.log("TAB - listener added")
}
