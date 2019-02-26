package pl.jug.view

import pl.jug.lib.RenderedView
import pl.jug.model.Attendee

abstract class AttendeesView : RenderedView() {
    abstract fun showMessage(text: String)
    abstract fun getAttendees(): List<Attendee>
}