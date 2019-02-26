package pl.jug.client

interface TabsClient {
    suspend fun getActiveTabId(): Int?
}