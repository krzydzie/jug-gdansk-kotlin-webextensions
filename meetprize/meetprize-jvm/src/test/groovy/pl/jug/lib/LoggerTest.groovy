package pl.jug.lib


import pl.jug.lib.testutils.LookupHelper
import pl.jug.lib.testutils.testconfiguration.ConfigurationHelper
import pl.jug.lib.testutils.testdata.TestClass
import pl.jug.lib.testutils.testdata.TestClassWithInfoLevel
import spock.lang.Specification

class LoggerTest extends Specification {
    ConfigurationHelper configurationHelper = new ConfigurationHelper()

    JsLogger jsLoggerMock = Mock()
    LookupHelper lookupHelper = new LookupHelper()
    TestClass testClass
    TestClassWithInfoLevel testClassWithInfoLevel


    def setup() {
        def configuration = configurationHelper.forLoggerTest(jsLoggerMock)
        new Application(configuration)
        testClass = lookupHelper.findTestClass()
        testClassWithInfoLevel = lookupHelper.findTestClassWithInfoLevel()
    }

    def "default level debug for trace"() {
        when: "trace level"
        testClass.logTrace("text")

        then:
        0 * jsLoggerMock.debug(*_)
    }

    def "default level debug for debug"() {
        when: "debug level"
        testClass.logDebug("text")

        then:
        1 * jsLoggerMock.debug("TestClass", "text")
    }

    def "default level debug for info"() {
        when: "info level"
        testClass.logInfo("text")

        then:
        1 * jsLoggerMock.info("TestClass", "text")
    }

    def "default level debug for error"() {
        when: "error level"
        testClass.logError("text")

        then:
        1 * jsLoggerMock.error("TestClass", "text")
    }


    def "changed level to info for trace"() {
        when: "trace level"
        testClassWithInfoLevel.logTrace("text")

        then:
        0 * jsLoggerMock.debug(*_)
    }

    def "changed level to info for debug"() {
        when: "debug level"
        testClassWithInfoLevel.logDebug("text")

        then:
        0 * jsLoggerMock.debug("TestClassWithInfoLevel", [["text"]])
    }

    def "changed level to info for info"() {
        when: "info level"
        testClassWithInfoLevel.logInfo("text")

        then:
        1 * jsLoggerMock.info("TestClassWithInfoLevel", "text")
    }

    def "changed level to info for error"() {
        when: "error level"
        testClassWithInfoLevel.logError("text")

        then:
        1 * jsLoggerMock.error("TestClassWithInfoLevel", "text")
    }
}
