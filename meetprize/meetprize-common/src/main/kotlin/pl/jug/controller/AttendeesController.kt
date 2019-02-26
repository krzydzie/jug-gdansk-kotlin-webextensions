package pl.jug.controller

import pl.jug.lib.*
import pl.jug.service.AttendeesService
import pl.jug.view.AttendeesView
import kotlin.reflect.KClass

class AttendeesController : PageController {
    private val logger = Logger.create<AttendeesController>()
    val attendeesView: AttendeesView by autowired()
    private val attendeesService: AttendeesService by autowired()


    override fun load() {
        attendeesService.startServer {
            attendeesView.getAttendees().also { list ->
                list.forEach { attendee ->
                    if (!attendee.profileUrl.contains("meetup.com")) {
                        attendee.profileUrl = "https://www.meetup.com${attendee.profileUrl}"
                    }
                }
            }
        }
    }
}