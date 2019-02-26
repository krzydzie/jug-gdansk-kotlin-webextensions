package pl.jug.lib.testutils

import pl.jug.lib.JsLogger
import pl.jug.lib.lookupBean
import pl.jug.lib.testutils.testdata.TestClass
import pl.jug.lib.testutils.testdata.TestClassWithInfoLevel
import pl.jug.lib.testutils.testdata.TestPage

class LookupHelper {
    fun findJsLogger(): JsLogger = lookupBean()
    fun findTestBean(): TestPage = lookupBean()
    fun findTestClass(): TestClass = lookupBean()
    fun findTestClassWithInfoLevel(): TestClassWithInfoLevel = lookupBean()

}
