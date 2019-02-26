fun bookmarkPageInFolder2(parentId: String, folderName: String, page: String) {
    async {
        val tree = bookmarks.getSubTree(parentId).await()

        val folderNode = tree[0].children?.find { it.title == folderName }
            ?: bookmarks.create(CreateDetails(parentId, folderName)).await()

        createBookmark2(folderNode.id, page)
    }
}

fun String.asUrl2() = run {
    if(this.contains(".")) this else "$this.pl"
}. run {
    if(this.indexOf("http") == 0) this else "https://$this"
}

fun createBookmark2(parentId: String, page: String) {
    bookmarks.create(CreateDetails(parentId, page, page.asUrl2())).catch {
        console.error("bookmarks.createBookmark error: ${it.message}")
    }
}

