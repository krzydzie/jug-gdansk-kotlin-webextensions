package pl.jug.lib.testutils.testdata

import pl.jug.lib.Logger


/**
 * TRACE, DEBUG, INFO, ERROR
 */
class TestClass {
    private val logger = Logger.create<TestClass>()

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