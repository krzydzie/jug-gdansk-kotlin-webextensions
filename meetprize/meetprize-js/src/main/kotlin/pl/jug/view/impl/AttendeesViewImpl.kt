package pl.jug.view.impl

import pl.jug.lib.jxQuery
import pl.jug.model.Attendee
import pl.jug.view.AttendeesView
import pl.treksoft.jquery.jQuery

class AttendeesViewImpl : AttendeesView() {
    override fun render() {
    }

    override fun getAttendees(): List<Attendee> {
        val result = mutableListOf<Attendee>()

        try {
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