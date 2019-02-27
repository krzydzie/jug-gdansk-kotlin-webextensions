package pl.jug.controller

import pl.jug.lib.Logger
import pl.jug.lib.PageController
import pl.jug.lib.autowired
import pl.jug.model.Attendee
import pl.jug.model.Winner
import pl.jug.service.AttendeesService
import pl.jug.service.WinnerBookmarkService
import pl.jug.view.LotteryView
import kotlin.random.Random

class LotteryController : PageController {

    private val logger = Logger.create<LotteryController>()
    private val lotteryView: LotteryView by autowired()
    private val attendeesService: AttendeesService by autowired()
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
            prizesField = listOf(
                    "Licencja na Jetbrains Community",
                    "Wejściówka na JUG'a",
                    "JUG'owe piwo"
            )

            randomCandidateButton = runIfNullCurrentCandidate {
                refreshAttendees()
                val prize = requireNotNull(currentPrize) { "Proszę podać nagrody" }
                currentCandidateField = Winner(prize, randomCandidate())
            }

            refreshCandidatesButton = { refreshAttendees(false) }

            confirmCandidateButton = {
                val winner = requireNotNull(currentCandidateField) { "Proszę wylosować kandydata." }
                winners += winner
                availablePrizes -= winner.prize
                currentCandidateField = currentPrize?.let { Winner(it, randomCandidate()) }
                winnerBookmarkService.bookmark(winner)
            }

            skipCandidateButton = {
                val candidate = requireNotNull(currentCandidateField) { "Proszę wylosować kandydata." }
                currentCandidateField = candidate.copy(attendee = randomCandidate())
            }
        }
    }

    private fun runIfNullCurrentCandidate(block: suspend () -> Unit): suspend () -> Unit = {
        if (lotteryView.currentCandidateField == null) {
            block()
        }
    }

    private fun randomCandidate(): Attendee {
        val attendee = getRandomCandidate()
        return attendee ?: throw IllegalStateException("Nie ma więcej kandydatów do losowania.")
    }

    private suspend fun refreshAttendees(onlyIfNull: Boolean = true) {
        if (onlyIfNull && this.attendees != null) {
            return
        }

        val attendees = requireNotNull(attendeesService.getAttendees()) { "Nieoczekiwana pusta lista uczestników" }
        logger.info("liczba uczestników: ${attendees.size}")
        require(attendees.isNotEmpty()) { "Attendee list cannot be empty" }
        attendeesToPick = attendees.toMutableList()
        this.attendees = attendees
    }

    private fun getRandomCandidate(): Attendee? {
        if (attendeesToPick.isEmpty()) return null
        val randomIndex = Random.nextInt(0, attendeesToPick.size)
        logger.info("randomIndex = $randomIndex")
        return attendeesToPick.removeAt(randomIndex)
    }
}


