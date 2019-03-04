package pl.jug.view.impl

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.div
import kotlinx.html.stream.createHTML
import pl.jug.html.*
import pl.jug.lib.*
import pl.jug.model.Winner
import pl.jug.view.LotteryView
import kotlin.browser.document
import kotlin.collections.set

class LotteryViewImpl : LotteryView() {

    override var prizesField: List<String> by ListTextareaDelegate()
    override var randomCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var refreshCandidatesButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var currentCandidateField: Winner? by HtmlDelegate(Winner::toHtml)

    override var confirmCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override var skipCandidateButton: AsyncAction by AsyncButtonCatchingDelegate()
    override val winners: ListElement<Winner> by HtmlListDelegate(Winner::toTableRow)

    override var linkMeetup: Action by ButtonDelegate()
    override var linkMeetupLocal: Action by ButtonDelegate()

    override fun render() {
        val self = this
        document.body!!.append {
            div(classes = "container") {
                span { +" Nagrody:" }
                br()

                textArea(self::prizesField, "Lista nagród")
                br()
                button(self::randomCandidateButton, "Losuj")
                div(self::currentCandidateField) { +"Kandydat" }
                button(self::confirmCandidateButton, "Tak")
                button(self::skipCandidateButton, "Nie")
                table(self::winners)
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
            +prize
        }
    }
}