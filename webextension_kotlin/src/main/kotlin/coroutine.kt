import kotlin.coroutines.*
import kotlin.js.Promise

suspend fun <T> Promise<T>.await(): T = suspendCoroutine { continuation ->
    this.then { value -> continuation.resume(value) }
    this.catch { exception -> continuation.resumeWithException(exception) }
}

fun <T> async(block: suspend () -> T) = Promise { resolve: (T) -> Unit, reject: (Throwable) -> Unit ->
    block.startCoroutine(completion = object : Continuation<T> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            result.onSuccess { value -> resolve(value) }
            result.onFailure { ex -> reject(ex) }
        }

    })
}