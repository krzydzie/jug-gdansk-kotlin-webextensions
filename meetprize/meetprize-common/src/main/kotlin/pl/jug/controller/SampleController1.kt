package pl.jug.controller

import pl.jug.client.WindowClient
import pl.jug.lib.Logger
import pl.jug.lib.PageController
import pl.jug.lib.autowired
import pl.jug.service.AttendeesService
import pl.jug.view.LotteryView

class SampleController1: PageController {
    private val logger = Logger.create<LotteryController>()
    private val lotteryView: LotteryView by autowired()
    private val attendeesService: AttendeesService by autowired()
    private val windowClient: WindowClient by autowired()

    override fun load() {
        with(lotteryView) {
//            prizesField = listOf()
//
//            randomCandidateButton = {
//                windowClient.alert("losowanie kliknięte")
//            }
//            confirmCandidateButton = {
//                windowClient.alert("tak kliknięte")
//            }
//            skipCandidateButton = {
//                windowClient.alert("nie kliknięte")
//            }
        }
    }
}