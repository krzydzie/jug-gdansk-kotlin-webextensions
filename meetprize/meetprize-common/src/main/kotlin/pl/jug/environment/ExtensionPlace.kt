package pl.jug.environment

enum class ExtensionPlace {
    SideBar, Content;

    companion object {
        fun valueOfPlace(place: String) = values().find { it.name.equals(place, ignoreCase = true) }
            ?: throw IllegalStateException("Unknown value of extensionPlace: $place")
    }
}