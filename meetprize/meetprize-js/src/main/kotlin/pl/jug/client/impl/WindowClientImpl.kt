package pl.jug.client.impl

import pl.jug.client.WindowClient
import kotlin.browser.window

class WindowClientImpl : WindowClient {
    override fun alert(text: String) {
        window.alert(text)
    }
}