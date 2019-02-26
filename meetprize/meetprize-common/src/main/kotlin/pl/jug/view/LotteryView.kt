package pl.jug.view

import pl.jug.html.ListElement
import pl.jug.lib.Action
import pl.jug.lib.AsyncAction
import pl.jug.lib.RenderedView
import pl.jug.model.Winner

abstract class LotteryView : RenderedView() {
    abstract var prizesField: List<String>
    abstract var randomCandidateButton: AsyncAction
    abstract var refreshCandidatesButton: AsyncAction
    abstract var currentCandidateField: Winner?
    abstract var confirmCandidateButton: AsyncAction
    abstract var skipCandidateButton: AsyncAction
    abstract val winners: ListElement<Winner>
    abstract var testWriteButton: AsyncAction
    abstract var linkMeetup: Action
    abstract var linkMeetupLocal: Action
}