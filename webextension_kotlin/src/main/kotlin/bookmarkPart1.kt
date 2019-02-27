val toolbarBookmarkId = "toolbar_____"

fun bookmarkPageInFolder1(parentId: String, folderName: String, page: String) {
    bookmarks.getSubTree(parentId).then {
        val folderNode= it[0].children?.find {it.title == folderName }

        if (folderNode == null) {
            bookmarks.create(CreateDetails(parentId, folderName)).then {
                createBookmark(it.id, page)
            }.catch {
                console.error("bookmarks.create error: ${it.message}")
            }
        } else {
            createBookmark(folderNode.id, page)
        }
    }.catch {
        console.error("bookmarks.getSubTree error: ${it.message}")
    }
}

fun createBookmark(parentId: String, page: String) {
    bookmarks.create(CreateDetails(parentId, page, stringAsUrl(page))).catch {
        console.error("bookmarks.createBookmark error: ${it.message}")
    }
}