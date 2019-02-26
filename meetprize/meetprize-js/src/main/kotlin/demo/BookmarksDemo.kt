package demo

import pl.jug.lib.async
import pl.jug.lib.await
import webextensions.browser

object BookmarksDemo {
    fun getTree() {
        async {
            browser.bookmarks.getTree().await()[0].children?.forEach {
                console.log("${it.title} = ${it.url}")
            }
        }

    }
}