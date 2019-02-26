package pl.jug.service

import pl.jug.client.BookmarksClient
import pl.jug.lib.autowired
import pl.jug.model.Winner

class WinnerBookmarkService {
    private val bookmarksClient: BookmarksClient by autowired()
    private val toolbarBookmarkId = "toolbar_____"

    fun bookmark(winner: Winner) {
        getToolbarTree()
    }

    private fun getToolbarTree() = bookmarksClient.getSubTree(toolbarBookmarkId)
}