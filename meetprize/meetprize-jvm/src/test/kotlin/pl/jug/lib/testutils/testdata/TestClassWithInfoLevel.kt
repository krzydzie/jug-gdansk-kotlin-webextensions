package pl.jug.lib.testutils.testdata

import pl.jug.lib.Logger

class TestClassWithInfoLevel {
    private var logger = Logger.create<TestClassWithInfoLevel>()

    fun logTrace(message: String) {
        logger.trace(message)
    }

    fun logDebug(message: String) {
        logger.debug(message)
    }

    fun logInfo(message: String) {
        logger.info(message)
    }

    fun logError(message: String) {
        logger.error(message)
    }
}