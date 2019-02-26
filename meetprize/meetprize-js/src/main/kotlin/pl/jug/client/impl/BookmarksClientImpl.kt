package pl.jug.client.impl

import pl.jug.client.BookmarksClient
import pl.jug.lib.await
import pl.jug.model.BookmarkTreeModel
import bookmarks.BookmarkTreeNode
import bookmarks.CreateDetails
import webextensions.browser

class BookmarksClientImpl: BookmarksClient {
    private val bookmarks = browser.bookmarks

    override suspend fun getSubTree(parentId: String) = let {
        bookmarks.getSubTree(parentId).await().asModelNode()
    }

    override suspend fun addFolder(parentId: String, name: String) = let {
        bookmarks.create(CreateDetails(parentId = parentId, title = name)).await().asModelNode()
    }

    override suspend fun addBookmark(parentId: String, name: String, url: String) = let {
        bookmarks.create(CreateDetails(parentId = parentId, title = name, url = url)).await().asModelNode()
    }

    private fun BookmarkTreeNode.asModelNode() = let {it ->
        val jsChildren = it.children ?: arrayOf()
        val children = mutableListOf<BookmarkTreeModel>()

        jsChildren.forEach {node ->
            children += BookmarkTreeModel(node.id, node.title)
        }

        BookmarkTreeModel(it.id, it.title, children)
    }

    private fun Array<BookmarkTreeNode>.asModelNode() = let {
        it[0].asModelNode()
    }

}