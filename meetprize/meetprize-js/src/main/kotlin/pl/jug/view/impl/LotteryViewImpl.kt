package pl.jug.view.impl

import kotlinx.html.a
import kotlinx.html.b
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.img
import kotlinx.html.js.div
import kotlinx.html.js.span
import kotlinx.html.stream.createHTML
import org.w3c.dom.get
import pl.jug.client.TabsClient
import pl.jug.html.*
import pl.jug.lib.*
import pl.jug.model.Attendee
import pl.jug.model.Winner
import pl.jug.view.LotteryView
import pl.treksoft.jquery.JQueryEventObject
import tabs.UpdateProperties
import webextensions.browser
import kotlin.browser.document
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
    override val winners: ListElement<Winner> by HtmlListDelegate(Winner::toHtml)

    val winnersOld: HtmlListElement<Winner> by HtmlListDelegateOld(Winner::toHtml)

    var testRead: ActionWithId by ButtonWithIdDelegate()
    val tabsClient: TabsClient by autowired()

    override var linkMeetup: Action by ButtonDelegate()
    override var linkMeetupLocal: Action by ButtonDelegate()

    override var testWriteButton: AsyncAction by AsyncButtonDelegate()
    private val meetupAddress = "https://www.meetup.com/Trojmiasto-Java-User-Group/events/256946831/attendees/"

    override fun render() {
        val self = this
        val body = document.getElementsByTagName("body")[0]!!
        body.append {
            div {
                a(
                    href = "#",
                    target = "_blank"
                ) {
                    attributes["id"] = "linkMeetup"
                    +"link"
                }
                +" | "
                a(
                    href = "#",
                    target = "_blank"
                ) {
                    attributes["id"] = "linkMeetupLocal"
                    +"local"
                }
                +" | "
                a(
                    href = "https://secure.meetupstatic.com/photos/member/a/b/c/1/member_266143969.jpeg",
                    target = "_blank"
                ) {
                    +"img"
                }
            }

            span { +"Nagrody:" }

            textArea(self::prizesField, "Lista nagród")

            button(self::randomCandidateButton, "Losuj")
            button(self::refreshCandidatesButton, "Odśwież kandydatów")
            div(self::currentCandidateField) { +"Kandydat" }
            button(self::confirmCandidateButton, "Tak")
            button(self::skipCandidateButton, "Nie")
            div(self::winners) { +"Zwycięzcy" }
            button(self::testRead, "Read")
            button(self::testWriteButton, "Write")
        }

        activeLinks()
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

    private fun addWinner() {
        val wc = Winner(
            "IntelliJ",
            Attendee(
                "Kowalski",
                "https://www.meetup.com/Trojmiasto-Java-User-Group/members/225895382/profile/",
                "https://secure.meetupstatic.com/photos/member/a/b/c/1/member_266143969.jpeg"
            )
        )
        winners += wc
    }
}

fun List<String>.toHtml() = joinToString(lineSeparator())

fun Winner.toHtml() = createHTML().div {
    b { +"$prize " }
    a(href = attendee.profileUrl, target = "_blank") {
        +attendee.name
    }
    img(alt = attendee.name, src = attendee.photoUrl, classes = "avatar")
}
