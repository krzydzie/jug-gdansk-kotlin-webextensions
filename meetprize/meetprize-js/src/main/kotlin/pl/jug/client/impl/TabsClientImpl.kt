package pl.jug.client.impl

import pl.jug.client.TabsClient
import pl.jug.lib.Logger
import pl.jug.lib.await
import tabs.QueryInfo
import webextensions.browser

class TabsClientImpl : TabsClient {
    private val logger = Logger.create<TabsClientImpl>()

    override suspend fun getActiveTabId(): Int? {
        logger.info("pobieram activeTabId")
        val activeTabArray =
            browser.tabs.query(QueryInfo(active = true, currentWindow = true)).await()
        logger.info("active tab")
        logger.info("id = ${activeTabArray[0]?.id}")

        return activeTabArray[0]?.id
    }
}