package pl.jug.service

import kotlinx.serialization.json.Json
import pl.jug.client.MessageClient
import pl.jug.client.MessageType
import pl.jug.client.Request
import pl.jug.client.Response
import pl.jug.lib.autowired
import pl.jug.model.Attendee


class AttendeesService {
    val messageClient: MessageClient by autowired()

    suspend fun getAttendees(): List<Attendee> {
        val value = messageClient.consumer.send(Request(MessageType.Attendees.name, "sending")).value
        return Json.parse(Attendee.serializer().list, value)
    }

    fun startServer(attendeesHandler: () -> List<Attendee>) {

        messageClient.producer.startServer(MessageType.Attendees) {
            val serialized = Json.stringify(Attendee.serializer().list, attendeesHandler())
            Response(MessageType.Attendees.name, serialized)
        }
    }
}



