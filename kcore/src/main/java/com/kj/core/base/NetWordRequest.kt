package com.kj.core.base

import kotlinx.coroutines.*

interface NetWordRequest {
    val mainScope: CoroutineScope
        get() = MainScope()

    fun <T> launchStart(
        block: suspend CoroutineScope.() -> T,
        success: (T?) -> Unit,
        error: (Exception) -> Unit = {},
        pre: () -> Unit = {},
        complete: () -> Unit = {}
    ) {
        mainScope.launch {
            try {
                pre()
                val result = withContext(Dispatchers.IO) { block() }
                success(result)
            } catch (e: Exception) {
                error(e)
            } finally {
                complete()
            }
        }
    }
}