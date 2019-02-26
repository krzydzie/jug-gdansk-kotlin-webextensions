package demo

import demo.jug.ContentPage
import demo.jug.SidebarPage
import pl.treksoft.jquery.jQuery

object JugDemo {

    operator fun invoke() {
        try {
            jQuery().ready {
                val place = getExtensionPlace() ?: throw RuntimeException("extensionPlace not set")
                when (place) {
                    "content" -> ContentPage()
                    "sidebar" -> SidebarPage()
                    else -> throw RuntimeException("unknown extensionPlace: $place")
                }
            }
        } catch (e: dynamic) {
            console.error("failed body css", e)
        }

    }
}

