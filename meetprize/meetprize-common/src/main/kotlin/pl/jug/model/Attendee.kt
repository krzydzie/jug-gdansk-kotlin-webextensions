package pl.jug.model

import kotlinx.serialization.Serializable

@Serializable
data class Attendee(var name: String = "", var profileUrl: String = "", var photoUrl: String = "")