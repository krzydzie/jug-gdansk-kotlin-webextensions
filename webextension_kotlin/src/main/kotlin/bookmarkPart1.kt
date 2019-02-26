val toolbarBookmarkId = "toolbar_____"

fun bookmarkPageInFolder(parentId: String, folderName: String, page: String) {
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
    bookmarks.create(CreateDetails(parentId, page, page.asUrl())).catch {
        console.error("bookmarks.createBookmark error: ${it.message}")
    }
}

fun String.asUrl() = run {
    if(this.contains(".")) this else "$this.pl"
}. run {
    if(this.indexOf("http") == 0) this else "http://$this"
}

fun zaczacOd(page: String): String {
    var url = if(page.contains(".")) page else "$page.pl"
    url = if(url.indexOf("http") == 0) url else "http://$url"

    return url
}

fun potem(page:String) = run {
    if(page.contains(".")) page else "$page.pl"
}. run {
    if(page.indexOf("http") == 0) page else "http://$page"
}
