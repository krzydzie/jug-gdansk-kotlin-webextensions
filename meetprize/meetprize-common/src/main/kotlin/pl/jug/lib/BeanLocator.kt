package pl.jug.lib

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object BeanLocator {
    lateinit var beanMappings: Map<KClass<out Any>, Any>
        private set

    fun initialize(beanMappings: Map<KClass<out Any>, Any>) {
        this.beanMappings = beanMappings
    }

    fun addReadyBean(instance: Any) {
        (instance as? RenderedView)?.also {
            it.renderView()
        }
    }

}

inline fun <reified T> autowired(): ReadOnlyProperty<Any?, T> = object : ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>) = lookupBean<T>()
}

inline fun <reified T> lookupBean(): T {
    val bean = BeanLocator.beanMappings[T::class] as? T
        ?: throw NoSuchElementException("Class ${T::class.simpleName} not registered")

    BeanLocator.addReadyBean(bean)

    return bean

}

inline fun <reified T> lookupBean(beanClass: KClass<out Any>): T {
    val bean = checkNotNull(BeanLocator.beanMappings[beanClass])
    { "Class ${beanClass::class.simpleName} not registered" }

    return bean as? T ?: throw IllegalStateException("${bean::class.simpleName} is not ${T::class.simpleName} type")
}
