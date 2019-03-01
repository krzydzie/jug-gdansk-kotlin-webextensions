# Description

2019-02-28 [Gdańsk Java User Group "Spotkanie 116: JS? We have to go deeper"](https://www.meetup.com/Trojmiasto-Java-User-Group/events/259129690/)

Power Point presentation in file [2019-02-28 JUG Webextension w Kotlinie.pptx](2019-02-28 JUG Webextension w Kotlinie.pptx)

Source code of sample webextensions used during the JUG meeting. 

Webextensions tested on Firefox 65.0.2

Used Webextensions API not applicable on Chrome or Opera without [Mozilla's polyfill](https://github.com/mozilla/webextension-polyfill) _(Differences in Promise/callback mechanizm)_

All extensions apply adding a bookmark in `Bookmarks Toolbar`. Do not forget to activate this toolbar during development as `web-ext` starts Firefox in a minimal form with this toolbar hidden.

Technologies under the hood:
1. [jQuery](https://jquery.com)
1. [Bootstrap](https://getbootstrap.com)

# Kotlin part
## Transcompilation to Javascript
Transcompilation to Javascript is done via Gradle task `other/runDceKotlinJs`. DCE stands for Dead Code Elimination which removes all unused Javascript code. Target compiled Javascript code is put into `build/kotlin-js-min/main/` folder. This path is used in html files for javascript paths. In case of problems look for where is the target of compiled javascript files. Only after the task is triggered the webextension is ready to use.

## Webextension development
web-ext is the best for Firefox. More about that:
* https://github.com/mozilla/web-ext/blob/master/README.md
* https://developer.mozilla.org/pl/docs/Mozilla/Add-ons/WebExtensions/Pierwsze_kroki_z_web-ext

Extensions is refreshed automatically after code changes.

## Available projects:
1. webextension - a sample extension in pure HTML and Javascript
1. webextension_kotlin - a sample extension in Kotlin
1. meetprize - a complex multiplatform extension that provides sample prize drawing during JUG

## Project "webextension_kotlin"
1. Sourcecode: `src/main/kotlin`
1. Main class: `Main.kt`

### webextension_kotlin file descriptions
* _bookmarkPart1.kt_ - Promise/callback version of handling bookmark operations
* _bookmarkPart2.kt_ - [coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) applied in bookmark operations
* _coroutine.kt_ - coroutines applied to Promises
* _js2kotlin.kt_ - "native" bookmark API interfaces in Kotlin, documentation on [Calling JavaScript from Kotlin](https://kotlinlang.org/docs/reference/js-interop.html) page  
* _stringUrl.kt_ - an old style transforming `page` into `url`
* _stringUrl2.kt_ - a scoping function used for page transforming, more about that on [Mastering Kotlin standard functions: run, with, let, also and apply](https://medium.com/@elye.project/mastering-kotlin-standard-functions-run-with-let-also-and-apply-9cd334b0ef84)
* _stringUrl3.kt_ - an application of extension for String, more about that on [Kotlin Extensions](https://kotlinlang.org/docs/reference/extensions.html) 

## Project "meetprize"
It is a multiplatform project. In the main folder is global `build.gradle`. Apart from that there are dedicated `build.gradle` in folders: meetprize-common, meetprize-js, meetprize-jvm:
* meetprize-jvm targets JVM platform and contains Spock unit tests
* meetprize-js targets Javascript platform 
* meetprize-common is a common part for all platforms

### Routing
There are two different views controlled by different controllers. But in both cases the same javascript files are used. To distinct the parts in the router a additional placeholder is injected:
* UI left sidebar lottery part
  * `manifest.json`
    * `sidebar_action`
      * `default_panel`: `sidebar/index.html`
        * `/meetprize-js/extension_place_sidebar.js`
          * `window.extensionPlace = "sidebar"`          
* meetup.com members page
  * `manifest.json`
    * `content_scripts`
      * `js`: `/meetprize-js/extension_place_content.js`
        * `window.extensionPlace = "content"`
* Controller resolution
  * meetprize-js/src/main/kotlin/pl.jug.environment.impl.ConfigurationImpl : `routing`

### Main file
meetprize-js/src/main/kotlin/Main

### Dependency Injection Bean Mapping
meetprize-js/src/main/kotlin/pl.jug.environment.impl.ConfigurationImpl : `beanMappings`

### Webextension API Clients
Implementations of accessing API clients: meetprize-js/src/main/kotlin/pl.jug.client.impl

### Coroutine extensions for Promise
meetprize-js/src/main/kotlin/pl.jug.lib.coroutine

### View implementation
meetprize-js/src/main/kotlin/pl.jug.view.impl
* LotteryViewImpl
* AttendeesViewImpl

### Controllers
meetprize-common/src/main/kotlin/pl.jug.controller
* LotteryController
* AttendeesController

### Communication between left Sidebar and Attendees list
Sidebar and Attendees list cannot communicate directly. It is done via messages.

`pl.jug.service.AttendeesService` handles it. It uses a Message API for that:
```kotlin
val messageClient: MessageClient by autowired()
```
* `getAttendees` is used by `LotteryController` as a client side
* `startServer` is used by `AttendeesController` as a server side

#### Message Content Layer
`pl.jug.model.Attendee` has an annotation `@Serializable`. It is the same mechanism like in Java Lombok library. The annotation provides an additional method in such a class `serializer()`. Thanks to that we do such stuff:
```kotlin
//serialization
val attendees: List<Attendee> = listOf(Attendee(a), Attendee(b))
val value: String = Json.stringify(Attendee.serializer().list, attendees)

//deserialization
val value: String = "..."
val attendees: List<Attendee> = Json.parse(Attendee.serializer().list, value)
```
*Hint* - In IntelliJ `.serializer()` may be highlighted as red indicated it as a compile red but it a false positive. Sometimes IDE cannot properly identify `@Serializable` existence in a class.

#### API communication
`Sidebar` sends message to Attendees Page _( [documentation](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/API/tabs/sendMessage) )_:
```javascript
//activeTabId - a resolved page tab with Attendees 
browser.tabs.sendMessage(activeTabId, message)
```

`Attendees Page` listens to that _( [documentation] (https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/API/runtime/onMessage) )_:
```javascript
browser.runtime.onMessage.addListener(listener) 
```

`Attendees Page` receives a message, serialize attendees and returns _( [documentation](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/API/runtime/sendMessage) )_:
```javascript
browser.runtime.sendMessage(message)
```

`Sidebar`  listens to that:
```javascript
browser.runtime.onMessage.addListener(listener)
```

#### `MessageClientImpl` communication
##### `Sidebar` - `pl.jug.client.impl.MessageConsumerImpl`
```kotlin
//listen to a response from Attendees Page
init {
    browser.runtime.onMessage.addListener(listener)
}

//sending a request for attendees
override suspend fun send(message: Request) = run {
    browser.tabs.sendMessage(activeTabId, message)
}
```

##### Sidebar coroutine support
It is required only on the Client side as the Server listen to request and at once responses. The Client sends asynchronously a request and has to sleep until a response arrives.

```kotlin
class MessageConsumerImpl : MessageConsumer {
    var continuation: Continuation<Response>? = null
    
    init {
        val listener = { message: Response ->
            //continuation.resume() makes the coroutine wake up in the listener
            continuation?.resume(message)
        }
    }
    
    override suspend fun send(message: Request) = run {
        //suspendCoroutine makes the coroutine sleep after browser.tabs.sendMessage()
        suspendCoroutine { it: Continuation<Response> ->
            continuation = it
            browser.tabs.sendMessage(activeTabId, message)
        }
    }
    
}
```

##### `Attendees Page` - `pl.jug.client.impl.MessageProducerImpl`
```kotlin
override fun startServer(messageType: MessageType, responseHandler: (Request) -> Response) {
    //listener that returns serialized attendees 
    val listener = { message: Request ->
          browser.runtime.sendMessage(message = responseHandler(message))
    }
    
    //listening to requests for attendees
    browser.runtime.onMessage.addListener(listener)
}
```

### Tricky techniques 
#### "Delegated Properties" [link](https://kotlinlang.org/docs/reference/delegated-properties.html)

E.g. `pl.jug.controller.LotteryController`
```kotlin
private val lotteryView: LotteryView by autowired()
```

#### "Overriding a member of an interface implemented by delegation" _( [documentation](https://kotlinlang.org/docs/reference/delegation.html) )_

In `common` platform we needs to trigger some actions for html on the javascript side but there is no access to that.
It is applied in `pl.jug.html.ListElement` which is used by `common` platform `pl.jug.view.LotteryView`. `ListElement` has `DomEntry` property where an implementation should be provided.
In javascript implementation `pl.jug.view.impl.LotteryViewImpl` there is:
```kotlin
override val winners: ListElement<Winner> by HtmlListDelegate(Winner::toTableRow)
```

In `HtmlListDelegate` there is `HtmlEntry` implementation for that
```kotlin
listElement = ListElement(property.name, renderer, HtmlEntry(property.name))
```