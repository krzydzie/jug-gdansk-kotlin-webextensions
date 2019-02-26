package demo

external val extensionPlace: String? = definedExternally

object ExtensionPlaceDemo {
    operator fun invoke() {
        console.log("extensionPlace = $extensionPlace")
    }
}