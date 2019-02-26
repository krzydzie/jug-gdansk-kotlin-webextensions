package pl.jug.lib


import pl.jug.lib.testutils.LookupHelper
import pl.jug.lib.testutils.testconfiguration.ConfigurationHelper
import pl.jug.lib.testutils.testdata.TestJsLogger
import spock.lang.PendingFeature
import spock.lang.Specification

class ApplicationTest extends Specification {
    def lookupHelper = new LookupHelper()
    TestJsLogger testJsLogger
    def configurationHelper = new ConfigurationHelper()

    def setup() {
        def testConfiguration = configurationHelper.forApplicationTest()
        new Application(testConfiguration)
        testJsLogger = (lookupHelper.findJsLogger() as TestJsLogger)
    }

    def "instance and configuration not null"() {
        expect:
        Application.instance != null
    }

    def "System logging active"() {
        expect:
        def infoLogs = testJsLogger.logs["info"]
        infoLogs != null
    }

    def "Log that beans registered"() {
        expect:
        testJsLogger.logs["info"].stream().findAll { it.contains("beans registered") }.size() == 1
    }

    def "Router created"() {

    }

    @PendingFeature
    def "init method was called"() {
//        expect:
//        1 * routerMock.routToController()

    }
}

