package demo.jug

import demo.extensionHasRun
import pl.jug.lib.jxQuery
import pl.treksoft.jquery.jQuery
import runtime.MessageSender
import webextensions.browser

object ContentPage {
    operator fun invoke() {
        console.log("hello world, content page")
        if (!extensionHasRun.value) {
            val listener = { message: JugMessageResponse, sender: MessageSender, sendResponse: () -> Unit ->
                console.log("content page - otrzymalem message command=${message.command}")
                if (message.command == "ping") {
                    console.log("content page - wysylam pong")
                    browser.runtime.sendMessage(message = JugMessage("pong"))
                }

                showAttendee()
            }

            browser.runtime.onMessage.addListener(listener)

        }


    }

    fun showAttendee() {
        try {
            val length = jxQuery("li.attendee-item.list-item").length
            console.log("liczba uczestnikow = $length")
            jxQuery("li.attendee-item.list-item").each { _, element ->
                val meetupProfile = jQuery(selector = "a", context = element).first().attr("href")
                val name =
                    jxQuery("span.avatar--person[role='img']", element).first().attr("aria-label")
                val img = jxQuery("img.avatar-print", element).first().attr("src")
                console.log(" - profile = $meetupProfile")
                console.log(" - name = $name")
                console.log(" - img = $img")
            }
        } catch (e: dynamic) {
            console.error("jest blad")
            console.error(e)
            console.dir(e)
        }
    }
}

