package pl.jug.controller

import pl.jug.lib.PageController
import pl.jug.lib.autowired
import pl.jug.service.AttendeesService
import pl.jug.view.AttendeesView

class AttendeesController : PageController {
    private val attendeesView: AttendeesView by autowired()
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