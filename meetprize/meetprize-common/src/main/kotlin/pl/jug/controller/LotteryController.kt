package pl.jug.controller

import pl.jug.client.BookmarksClient
import pl.jug.client.WindowClient
import pl.jug.lib.*
import pl.jug.model.Attendee
import pl.jug.model.Winner
import pl.jug.service.AttendeesService
import pl.jug.service.WinnerBookmarkService
import pl.jug.view.LotteryView
import kotlin.random.Random

/**
 * pole na wpisanie nagrod
 * button do pobrania listy uczestnikow
 * textarea do wyswietlenia listy uczestnikow
 *
 */
class LotteryController : PageController {

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

    private var candidateRandomizer: Sequence<Attendee?>? = null

    override fun load() {
        with(lotteryView) {
            prizesField = listOf("aaa", "bbb", "ccc")

            randomCandidateButton = runIfNullCurrentCandidate {
                refreshAttendees()
                val prize = requireNotNull(currentPrize) { "Proszę podać nagrody" }
                currentCandidateField = Winner(prize, randomCandidate())
            }

            refreshCandidatesButton = { refreshAttendees(false) }

            confirmCandidateButton = {
                val winner = requireNotNull(currentCandidateField) { "Proszę wylosować kandydata." }
                winners += winner
                winnerBookmarkService.bookmark(winner)
                availablePrizes -= winner.prize
                currentCandidateField = currentPrize?.let { Winner(it, randomCandidate()) }
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
//        val randomizer = requireNotNull(candidateRandomizer) { "candidateRandomizer nie skonfigurowany" }
//        val attendee = randomizer.singleOrNull()
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
//        candidateRandomizer = createRandomizer(attendees)
        this.attendees = attendees
    }

    private fun getRandomCandidate(): Attendee? {
        if (attendeesToPick.isEmpty()) return null
        val randomIndex = Random.nextInt(0, attendeesToPick.size)
        logger.info("randomIndex = $randomIndex")
        return attendeesToPick.removeAt(randomIndex).also {
            logger.info("wylosowani $it")
        }
    }

    private fun createRandomizer(attendees: List<Attendee>) = run {
        val attendeesToPick = attendees.toMutableList()
        logger.info("poczatkowa liczba do losowania = ${attendeesToPick.size}")
        fun randomIndex() = run {
            logger.info("Random.nextInt(0, attendeesToPick.size = ${attendeesToPick.size} )")
            Random.nextInt(0, attendeesToPick.size).also {
                logger.info("wylosowana liczba $it")
            }
        }

        sequence {
            logger.info("petla dziala poki not empty ${attendees.isNotEmpty()}")
            while (attendees.isNotEmpty()) {
                val index = randomIndex()
                logger.info("index = $index")
                attendeesToPick.removeAt(index).also { candidate ->
                    logger.info("wylosowany kandydat $candidate")
                    yield(candidate)
                }
            }
            logger.info("lista jest juz pusta, zwraca null")

            while (true) yield(null)
        }
    }


    private fun getDummyAttendee() = Attendee(
        "Kowalski",
        "https://www.meetup.com/",
        "https://secure.meetupstatic.com/photos/member/a/b/c/1/member_266143969.jpeg"
    )
}


