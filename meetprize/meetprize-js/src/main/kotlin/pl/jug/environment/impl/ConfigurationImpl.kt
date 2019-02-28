package pl.jug.environment.impl

import pl.jug.client.BookmarksClient
import pl.jug.client.MessageClient
import pl.jug.client.TabsClient
import pl.jug.client.WindowClient
import pl.jug.client.impl.BookmarksClientImpl
import pl.jug.client.impl.MessageClientImpl
import pl.jug.client.impl.TabsClientImpl
import pl.jug.client.impl.WindowClientImpl
import pl.jug.controller.AttendeesController
import pl.jug.controller.Demo1
import pl.jug.controller.LotteryController
import pl.jug.environment.Configuration
import pl.jug.environment.ExtensionPlace
import pl.jug.lib.LogLevel
import pl.jug.lib.impl.ConsoleLogger
import pl.jug.lib.impl.RouterImpl
import pl.jug.service.AttendeesService
import pl.jug.service.WinnerBookmarkService
import pl.jug.view.AttendeesView
import pl.jug.view.LotteryView
import pl.jug.view.impl.AttendeesViewImpl
import pl.jug.view.impl.LotteryViewImpl

object ConfigurationImpl : Configuration {
    init {
        kotlin.js.Date().getTime().toLong()
    }
    override val systemLogActive = true
    override val defaultLogLevel = LogLevel.DEBUG

    override val classLogLevel = mapOf<String, LogLevel>()
    override val jsLogger = ConsoleLogger()

    override val routing = mapOf(
        ExtensionPlace.SideBar to Demo1::class,
        ExtensionPlace.Content to AttendeesController::class
    )

    override val router = { RouterImpl(routing) }

    override val beanMappings = mapOf(
        TabsClient::class to TabsClientImpl(),
        MessageClient::class to MessageClientImpl(),
        LotteryView::class to LotteryViewImpl(),
        LotteryController::class to LotteryController(),
        AttendeesView::class to AttendeesViewImpl(),
        AttendeesController::class to AttendeesController(),
        Demo1::class to Demo1(),
        AttendeesService::class to AttendeesService(),
        WindowClient::class to WindowClientImpl(),
        BookmarksClient::class to BookmarksClientImpl(),
        WinnerBookmarkService::class to WinnerBookmarkService()
    )
}
