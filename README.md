# Description

Source code of webextensions used during [Gdańsk Java User Group "Spotkanie 116: JS? We have to go deeper"](https://www.meetup.com/Trojmiasto-Java-User-Group/events/259129690/) conducted on 28.02.2019

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
1. meetprize - a complex multiplatform extension that provides prize drawing during JUG

## Project webextension_kotlin
1. Sourcecode: `src/main/kotlin`
1. Main class: `Main.kt`

### webextension_kotlin file descriptions
* bookmarkPart1.kt - Promise/callback version of handling bookmark operations
* bookmarkPart2.kt - coroutines applied in bookmark operations
* coroutine.kt - coroutines applied to Promises
* js2kotlin.kt - "native" bookmark API interfaces in Kotlin, documentation on [Calling JavaScript from Kotlin](https://kotlinlang.org/docs/reference/js-interop.html) page  
* stringUrl.kt - an old style transforming `page` into `url`
* stringUrl2.kt - a scoping function used for page transforming, more about that on [Mastering Kotlin standard functions: run, with, let, also and apply](https://medium.com/@elye.project/mastering-kotlin-standard-functions-run-with-let-also-and-apply-9cd334b0ef84)
* stringUrl3.kt - an application of extension for String, more about that on [Kotlin Extensions](https://kotlinlang.org/docs/reference/extensions.html) 

## Project meetprize
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


