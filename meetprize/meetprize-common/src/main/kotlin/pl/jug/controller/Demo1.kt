package pl.jug.controller

import pl.jug.client.WindowClient
import pl.jug.lib.Logger
import pl.jug.lib.PageController
import pl.jug.lib.autowired
import pl.jug.model.Attendee
import pl.jug.service.AttendeesService
import pl.jug.service.WinnerBookmarkService
import pl.jug.view.LotteryView
import kotlin.random.Random

class Demo1: PageController {
    private val logger = Logger.create<LotteryController>()
    private val lotteryView: LotteryView by autowired()
    private val attendeesService: AttendeesService by autowired()
    private val windowClient: WindowClient by autowired()
    private val winnerBookmarkService: WinnerBookmarkService by autowired()

    private var availablePrizes: List<String>
        get() = lotteryView.prizesField.map { it.trim() }.filter { it.isNotEmpty() }

        set(value) {
            lotteryView.prizesField = value
        }

    private val currentPrize: String?
        get() = availablePrizes.firstOrNull()

    private var attendees: List<Attendee>? = null

    private var attendeesToPick: MutableList<Attendee> = mutableListOf()

    override fun load() {
        with(lotteryView) {
            //Lista nagród
            prizesField = listOf(
                    "nagroda"
            )

            //Losowanie pierwszej nagrody
            randomCandidateButton = {
                windowClient.alert("losowanie kliknięte")
            }

            //kandydat zatwierdzony, losuje następnego
            confirmCandidateButton = {
                windowClient.alert("TAK")
            }

            //kandydat odrzucony, losuje następneg
            skipCandidateButton = {
                windowClient.alert("NIE")
            }
        }
    }

    private fun runIfNoneCandidatePicked(block: suspend () -> Unit): suspend () -> Unit = {
        if (lotteryView.currentCandidateField == null) {
            block()
        }
    }

    private fun randomCandidate(): Attendee {
        val attendee = getRandomCandidate()
        return attendee ?: throw IllegalStateException("Nie ma więcej kandydatów do losowania.")
    }

    private suspend fun refreshAttendees() {
        if (this.attendees != null) {
            return
        }

        val attendees = requireNotNull(attendeesService.getAttendees()) { "Nieoczekiwana pusta lista uczestników" }
        require(attendees.isNotEmpty()) { "Attendee list cannot be empty" }
        attendeesToPick = attendees.toMutableList()
        this.attendees = attendees
    }

    private fun getRandomCandidate(): Attendee? {
        if (attendeesToPick.isEmpty()) return null
        val randomIndex = Random.nextInt(0, attendeesToPick.size)
        return attendeesToPick.removeAt(randomIndex)
    }

}