package com.kj.core.base

import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus

data class BaseEvent<T>(val id: Int, val data: T? = null)

class BusUtil private constructor(): CoroutineScope by MainScope(){
    private val bus by lazy {
        EventBus.getDefault()
    }

    companion object {
        fun get() = BusUtilHolder.instance
    }

    private object BusUtilHolder {
        val instance = BusUtil()
    }

    fun register(subscriber: Any) = bus.register(subscriber)

    fun unRegister(subscriber: Any) = bus.unregister(subscriber)

    fun <T> sendEvent(event: BaseEvent<T>) = bus.post(event)

    fun <T> postDelay(timeMillis: Long, block: () -> T)  = launch {
        withContext(Dispatchers.IO) {
            delay(timeMillis)
        }
        block()
    }

    fun postEvent(event: Any) = bus.post(event)
}