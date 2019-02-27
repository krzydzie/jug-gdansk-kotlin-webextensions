package pl.jug.view.impl

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.div
import kotlinx.html.stream.createHTML
import pl.jug.client.TabsClient
import pl.jug.html.*
import pl.jug.lib.*
import pl.jug.model.Winner
import pl.jug.view.LotteryView
import pl.treksoft.jquery.JQueryEventObject
import tabs.UpdateProperties
import webextensions.browser
import kotlin.browser.document
import kotlin.collections.List
import kotlin.collections.joinToString
import kotlin.collections.set
import kotlin.js.Promise

/*
Napis "root folder dla wygranych" select, przycisk zapisz
Napis "wygrani zapisywani w folderze xxx"

 */
class LotteryViewImpl : LotteryView() {

    override var prizesField: List<String> by ListTextareaDelegate()
    override var randomCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var refreshCandidatesButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var currentCandidateField: Winner? by HtmlDelegate(Winner::toHtml)

    override var confirmCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var skipCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override val winners: ListElement<Winner> by HtmlListDelegate(Winner::toTableRow)

    val tabsClient: TabsClient by autowired()

    override var linkMeetup: Action by ButtonDelegate()
    override var linkMeetupLocal: Action by ButtonDelegate()

    private val meetupAddress = "https://www.meetup.com/Trojmiasto-Java-User-Group/events/259129690/attendees/"

    override fun render() {
        val self = this
//        val body = document.getElementsByTagName("body")[0]!!
        document.body!!.append {
            div(classes = "container") {
                a(
                        href = "#",
                        target = "_blank"
                ) {
                    attributes["id"] = "linkMeetup"
                    +"JUG"
                }

                span { +" Nagrody:" }
                br()

                textArea(self::prizesField, "Lista nagród")
                br()
                button(self::randomCandidateButton, "Losuj")
                button(self::refreshCandidatesButton, "Odśwież kandydatów")
                div(self::currentCandidateField) { +"Kandydat" }
                button(self::confirmCandidateButton, "Tak")
                button(self::skipCandidateButton, "Nie")
//                div(self::winners) { +"Zwycięzcy" }
                table(self::winners)

//                sampleTable()
            }
        }

        activeLinks()
    }

    private fun DIV.sampleTable() {
        table(classes = "table table-striped table-bordered") {
            thead {
                tr {
                    th(scope = ThScope.col) {
                        +"One"
                    }
                    th(scope = ThScope.col) {
                        +"Two"
                    }
                }
            }
            tbody {
                tr {
                    td {
                        +"abc"
                    }
                    td {
                        +"def"
                    }
                }
                tr {
                    td {
                        +"abc"
                    }
                    td {
                        +"def"
                    }
                }
                tr {
                    td {
                        +"abc"
                    }
                    td {
                        +"def"
                    }
                }
            }
        }
    }

    private fun activeLinks() {
        ::linkMeetup.elementById().click { gotoUrl(it, meetupAddress) }
    }

    private fun gotoUrl(it: JQueryEventObject, url: String): Promise<Any> {
        it.preventDefault()

        return async {
            val activeId = tabsClient.getActiveTabId()

            if (activeId == null) {
                console.error("brak aktywnego tab id ")
            } else {
                browser.tabs.update(activeId, UpdateProperties(url))
            }
        }
    }

}

fun List<String>.toHtml() = joinToString(lineSeparator())

fun Winner.toHtml() = createHTML().div {
    div(classes = "m-1") { hr() }
    img(alt = attendee.name, src = attendee.photoUrl, classes = "avatar img-thumbnail rounded-circle m-1")
    a(href = attendee.profileUrl, target = "_blank", classes = "m-1") { +attendee.name }

    div(classes = "alert alert-primary") {
        attributes["role"] = "alert"
        +"$prize "
    }
}

fun Winner.toTableRow() = createHTML().tr {
    td {
        img(alt = attendee.name, src = attendee.photoUrl, classes = "avatar img-thumbnail rounded-circle")
    }

    td {
        a(href = attendee.profileUrl, target = "_blank") {
            +attendee.name
        }
    }

    td {
        b {
            + prize
        }
    }
}