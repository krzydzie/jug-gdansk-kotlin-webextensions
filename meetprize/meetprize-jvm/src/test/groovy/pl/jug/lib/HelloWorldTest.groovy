package pl.jug.lib

import spock.lang.Specification

class HelloWorldTest extends Specification {

    def 'Sprawdza rowność'() {
        expect:
        a == b

        where:
        a || b
        1 || 1
        2 || 2
        4 || 4
    }
}
