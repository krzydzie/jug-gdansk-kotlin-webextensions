import kotlin.js.Promise

external val browser: BrowserApi
val bookmarks = browser.bookmarks

external class BrowserApi {
    val bookmarks: BookmarksApi

}

external class BookmarksApi {
    fun getSubTree(id: String): Promise<Array<BookmarkTreeNode>>
    fun create(bookmark: CreateDetails): Promise<BookmarkTreeNode>
}

class BookmarkTreeNode(
    var id: String,
    var parentId: String? = null,
    var url: String? = null,
    var title: String,
    var children: Array<BookmarkTreeNode>? = null
)

class CreateDetails(
    var parentId: String? = null,
    var title: String? = null,
    var url: String? = null
)


