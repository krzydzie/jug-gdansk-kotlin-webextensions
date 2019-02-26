package demo

import extensionTypes.InjectDetails
import pl.jug.lib.jxQuery
import pl.treksoft.jquery.jQuery
import webextensions.browser

object ExecuteScript {
    fun sample() {

        jQuery().ready {
            jxQuery("#actionButton").click {
                browser.tabs.executeScript(details = InjectDetails(code = "console.log('location:', window.location.href);"))
            }
        }
    }
}