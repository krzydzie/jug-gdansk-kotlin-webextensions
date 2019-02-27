package pl.jug.service

import pl.jug.client.BookmarksClient
import pl.jug.lib.autowired
import pl.jug.model.Winner

class WinnerBookmarkService {
    private val bookmarksClient: BookmarksClient by autowired()
    private val TOOLBAR_BOOKMARK_ID = "toolbar_____"
    private val JUG = "JUG"

    suspend fun bookmark(winner: Winner) {
        val jugFolder = getToolbarTree().children.find { it.title == JUG }
            ?: bookmarksClient.addFolder(TOOLBAR_BOOKMARK_ID, JUG)

        bookmarksClient.addBookmark(jugFolder.id, winner.bookmarkName, winner.attendee.profileUrl)
    }

    private suspend fun getToolbarTree() = bookmarksClient.getSubTree(TOOLBAR_BOOKMARK_ID)

    private val Winner.bookmarkName: String
        get() = "$prize - ${attendee.name}"
}