package pl.jug.client.impl

import pl.jug.client.TabsClient
import pl.jug.lib.await
import tabs.QueryInfo
import webextensions.browser

class TabsClientImpl : TabsClient {

    override suspend fun getActiveTabId(): Int? {
        val activeTabArray =
            browser.tabs.query(QueryInfo(active = true, currentWindow = true)).await()

        return activeTabArray[0]?.id
    }
}