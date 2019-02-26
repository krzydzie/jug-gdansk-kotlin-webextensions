package pl.jug.lib.async

import kotlin.coroutines.*

class Promise<T> {
    private lateinit var continuation: Continuation<T>

    suspend fun await(): T = suspendCoroutine { continuation ->
        this.continuation = continuation
    }

    fun resolve(value: T) {
        continuation.resume(value)
    }

    fun reject(ex: Throwable) {
        continuation.resumeWithException(ex)
    }

    fun <T> async(block: suspend () -> T) {
        val self = this
        block.startCoroutine(completion = object : Continuation<T> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
//                result.onSuccess { value -> self.resolve(value) }
                result.onFailure { ex -> self.reject(ex) }
            }
        })
    }
}

fun <T> async(block: suspend () -> T): Promise<T> {
    //bookmkarks.sendMessage()
    //i tu suspend
    // w onListen musi byc wybudzenie
    //musimy miec dostep do Promise resolve albo rejest
    val promise = Promise<T>()

    block.startCoroutine(completion = object : Continuation<T> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            result.onSuccess { value -> promise.resolve(value) }
            result.onFailure { ex -> promise.reject(ex) }
        }
    })

    return promise
}