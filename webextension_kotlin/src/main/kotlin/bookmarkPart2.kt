fun bookmarkPageInFolder2(parentId: String, folderName: String, page: String) {
    async {
        val tree = bookmarks.getSubTree(parentId).await()

        val folderNode = tree[0].children?.find { it.title == folderName }
            ?: bookmarks.create(CreateDetails(parentId, folderName)).await()

        createBookmark2(folderNode.id, page)
    }
}

fun createBookmark2(parentId: String, page: String) {
    bookmarks.create(CreateDetails(parentId, page, page.asUrl())).catch {
        console.error("bookmarks.createBookmark error: ${it.message}")
    }
}

