package demo

import pl.jug.lib.QUnit
import pl.jug.lib.jxQuery
import pl.treksoft.jquery.jQuery

object QunitDemo {
    fun sample() {
        QUnit.test("hello test") {
            it.ok(1.toString() == "1", "Passed")
        }

        jQuery().ready {
            jxQuery("#startQunit").click {
                console.log("qunit started")
                QUnit.start()
            }
        }


    }
}