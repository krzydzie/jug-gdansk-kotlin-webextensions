package pl.jug.model

data class BookmarkTreeModel(val id: String, val title: String, val children: List<BookmarkTreeModel> = listOf())
