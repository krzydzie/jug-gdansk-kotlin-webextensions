package pl.jug.client

import pl.jug.model.BookmarkTreeModel

interface BookmarksClient {
    suspend fun getSubTree(parentId: String): BookmarkTreeModel
    suspend fun addFolder(parentId: String, name: String): BookmarkTreeModel
    suspend fun addBookmark(parentId: String, name: String, url: String): BookmarkTreeModel
}