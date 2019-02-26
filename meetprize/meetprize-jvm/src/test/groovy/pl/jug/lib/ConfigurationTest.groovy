package pl.jug.lib

import pl.jug.lib.testutils.LookupHelper
import pl.jug.lib.testutils.testconfiguration.ConfigurationHelper
import pl.jug.lib.testutils.testdata.TestJsLogger
import spock.lang.Specification

class ConfigurationTest extends Specification {
    def configurationHelper = new ConfigurationHelper()
    def lookupHelper = new LookupHelper()

    def "Switch off system logs"() {
        def configuration = configurationHelper.forConfigurationTest()
        new Application(configuration)
        def testJsLogger = (lookupHelper.findJsLogger() as TestJsLogger)

        expect:
        testJsLogger.logs["info"] == null || testJsLogger.logs["info"].isEmpty()
    }
}
