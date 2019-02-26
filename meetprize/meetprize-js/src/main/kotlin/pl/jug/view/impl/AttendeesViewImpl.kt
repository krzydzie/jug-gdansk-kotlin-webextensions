package pl.jug.view.impl

import pl.jug.lib.jxQuery
import pl.jug.model.Attendee
import pl.jug.view.AttendeesView
import pl.treksoft.jquery.jQuery
import webextensions.browser

class AttendeesViewImpl : AttendeesView() {
    override fun render() {
        console.log("=== AttendeesViewImpl")
//        ContentPage()
    }

    override fun showMessage(text: String) {
        console.log("AttendeesViewImpl text: $text")
        showAttendee()
    }

    fun showAttendee() {
        try {
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
            throw e
        }
    }

    override fun getAttendees(): List<Attendee> {
        val result = mutableListOf<Attendee>()

        try {
            /**
             * todo zrobic ladniej wyciaganie uczestnikow
             */
            jxQuery("li.attendee-item.list-item").each { _, element ->
                val meetupProfile = jQuery(selector = "a", context = element).first().attr("href")
                val name =
                    jxQuery("span.avatar--person[role='img']", element).first().attr("aria-label")
                val img = jxQuery("img.avatar-print", element).first().attr("src")
                result += Attendee(name, meetupProfile, img)
                Unit
            }
        } catch (e: dynamic) {
            console.error("jest blad")
            console.error(e)
            console.dir(e)
            throw e
        }

        return result
    }
}