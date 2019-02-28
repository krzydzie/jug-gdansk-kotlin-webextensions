package pl.jug.html

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.jug.client.WindowClient
import pl.jug.lib.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ButtonDelegate : ReadWriteProperty<Any, Action> {
    private lateinit var block: Action

    override fun getValue(thisRef: Any, property: KProperty<*>): Action = block

    override fun setValue(thisRef: Any, property: KProperty<*>, block: Action) {
        this.block = block
        property.elementById().click { block() }
    }
}

class AsyncButtonDelegate : ReadWriteProperty<Any, AsyncAction> {
    private lateinit var block: AsyncAction

    override fun getValue(thisRef: Any, property: KProperty<*>): AsyncAction = block


    override fun setValue(thisRef: Any, property: KProperty<*>, block: AsyncAction) {
        this.block = block

        property.elementById().click {
            GlobalScope.launch {
                block()
            }
        }
    }
}

class AsyncButtonCatchingDelegate(private val defaultErrorMessage: String = "Operacja nie udana.") :
    ReadWriteProperty<Any, AsyncAction> {
    private val windowClient: WindowClient by autowired()
    private lateinit var block: AsyncAction

    override fun getValue(thisRef: Any, property: KProperty<*>): AsyncAction = block


    override fun setValue(thisRef: Any, property: KProperty<*>, block: AsyncAction) {
        this.block = block

        property.elementById().click {
            GlobalScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    windowClient.alert(e.message ?: defaultErrorMessage)
                }
            }
        }
    }
}

class AsyncButtonResultDelegate<T>(private val defaultErrorMessage: String = "Operacja nie udana.") :
    ReadWriteProperty<Any, AsyncResultAction<T>> {
    private val windowClient: WindowClient by autowired()
    private lateinit var block: AsyncResultAction<T>

    override fun getValue(thisRef: Any, property: KProperty<*>): AsyncResultAction<T> = block

    override fun setValue(thisRef: Any, property: KProperty<*>, block: AsyncResultAction<T>) {
        this.block = block

        property.elementById().click {
            GlobalScope.launch {
                block().onFailure { e ->
                    windowClient.alert(e.message ?: defaultErrorMessage)
                }
            }
        }
    }
}

class ButtonWithIdDelegate : ReadWriteProperty<Any, ActionWithId> {
    private lateinit var block: ActionWithId

    override fun getValue(thisRef: Any, property: KProperty<*>): ActionWithId = block

    override fun setValue(thisRef: Any, property: KProperty<*>, block: ActionWithId) {
        this.block = block
        property.elementById().click { block(property.name) }
    }
}