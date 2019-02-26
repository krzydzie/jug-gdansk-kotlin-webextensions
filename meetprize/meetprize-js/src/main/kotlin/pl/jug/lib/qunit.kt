@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package pl.jug.lib

external interface DoneCallbackObject {
    var failed: Number
    var passed: Number
    var total: Number
    var runtime: Number
}
external interface LogCallbackObject {
    var result: Boolean
    var actual: Any
    var expected: Any
    var message: String
    var source: String
}
external interface ModuleStartCallbackObject {
    var name: String
}
external interface ModuleDoneCallbackObject {
    var name: String
    var failed: Number
    var passed: Number
    var total: Number
}
external interface TestDoneCallbackObject {
    var name: String
    var module: String
    var failed: Number
    var passed: Number
    var total: Number
    var duration: Number
}
external interface TestStartCallbackObject {
    var name: String
    var module: String
}
external interface Config {
    var altertitle: Boolean
    var autostart: Boolean
    var current: Any
    var reorder: Boolean
    var requireExpects: Boolean
    var testTimeout: Number
    var urlConfig: Array<URLConfigItem>
    var done: Any
}
external interface URLConfigItem {
    var id: String
    var label: String
    var tooltip: String
}
external interface LifecycleObject {
    var setup: (() -> Any)? get() = definedExternally; set(value) = definedExternally
    var teardown: (() -> Any)? get() = definedExternally; set(value) = definedExternally
}
external interface QUnitAssert {
    var assert: Any
    var current_testEnvironment: Any
    var jsDump: Any
    fun async(): Any
    fun deepEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun equal(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun expect(amount: Number): Any
    fun notDeepEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun notEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun notPropEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun propEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun notStrictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun ok(state: Any, message: String? = definedExternally /* null */): Any
    fun strictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun throws(block: () -> Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun throws(block: () -> Any, message: String? = definedExternally /* null */): Any
    fun raises(block: () -> Any, expected: Any, message: String? = definedExternally /* null */): Any
    fun raises(block: () -> Any, message: String? = definedExternally /* null */): Any
}
external interface QUnitStatic : QUnitAssert {
    fun start(decrement: Number? = definedExternally /* null */): Any
    fun stop(increment: Number? = definedExternally /* null */): Any
    fun begin(callback: () -> Any): Any
    fun done(callback: (details: DoneCallbackObject) -> Any): Any
    fun log(callback: (details: LogCallbackObject) -> Any): Any
    fun moduleDone(callback: (details: ModuleDoneCallbackObject) -> Any): Any
    fun moduleStart(callback: (details: ModuleStartCallbackObject) -> Any): Any
    fun testDone(callback: (details: TestDoneCallbackObject) -> Any): Any
    fun testStart(callback: (details: TestStartCallbackObject) -> Any): Any
    var config: Config
    fun asyncTest(name: String, expected: Number, test: (assert: QUnitAssert) -> Any): Any
    fun asyncTest(name: String, test: (assert: QUnitAssert) -> Any): Any
    override fun expect(amount: Number): Any
    fun module(name: String, lifecycle: LifecycleObject? = definedExternally /* null */): Any
    fun test(title: String, expected: Number, test: (assert: QUnitAssert) -> Any): Any
    fun test(title: String, test: (assert: QUnitAssert) -> Any): Any
    fun equiv(a: Any, b: Any): Any
    fun push(result: Any, actual: Any, expected: Any, message: String): Any
    fun reset(): Any
}
external fun deepEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun equal(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun notDeepEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun notEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun notStrictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun ok(state: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun strictEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun throws(block: () -> Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun throws(block: () -> Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun start(decrement: Number? = definedExternally /* null */): Any = definedExternally
external fun stop(increment: Number? = definedExternally /* null */): Any = definedExternally
external fun begin(callback: () -> Any): Any = definedExternally
external fun done(callback: (details: DoneCallbackObject) -> Any): Any = definedExternally
external fun log(callback: (details: LogCallbackObject) -> Any): Any = definedExternally
external fun moduleDone(callback: (details: ModuleDoneCallbackObject) -> Any): Any = definedExternally
external fun moduleStart(callback: (name: String) -> Any): Any = definedExternally
external fun testDone(callback: (details: TestDoneCallbackObject) -> Any): Any = definedExternally
external fun testStart(callback: (details: TestStartCallbackObject) -> Any): Any = definedExternally
external fun asyncTest(name: String, expected: Any? = definedExternally /* null */, test: ((assert: QUnitAssert) -> Any)? = definedExternally /* null */): Any = definedExternally
external fun asyncTest(name: String, test: (assert: QUnitAssert) -> Any): Any = definedExternally
external fun expect(amount: Number): Any = definedExternally
external fun test(title: String, expected: Number, test: (assert: QUnitAssert? /*= null*/) -> Any): Any = definedExternally
external fun test(title: String, test: (assert: QUnitAssert? /*= null*/) -> Any): Any = definedExternally
external fun notPropEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun propEqual(actual: Any, expected: Any, message: String? = definedExternally /* null */): Any = definedExternally
external fun equiv(a: Any, b: Any): Any = definedExternally
external var raises: Any = definedExternally
external var QUnit: QUnitStatic = definedExternally
