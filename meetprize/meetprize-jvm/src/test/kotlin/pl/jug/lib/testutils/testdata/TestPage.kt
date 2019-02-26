package pl.jug.lib.testutils.testdata

import pl.jug.lib.PageController
import pl.jug.lib.autowired

class TestPage : PageController {
    val testView: TestView by autowired()
    var loadWasCalled = false

    override fun load() {
        loadWasCalled = true
    }
}