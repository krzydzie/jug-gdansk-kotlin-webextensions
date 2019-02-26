package pl.jug.client

import pl.jug.model.BookmarkTreeNode

interface BookmarksClient {
    fun getSubTree(parentId: String): BookmarkTreeNode
}