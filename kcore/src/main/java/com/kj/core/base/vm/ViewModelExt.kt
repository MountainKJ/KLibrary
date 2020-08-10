package com.kj.core.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM: BaseViewModel, A> singleArgViewModelFactory(constructor: (A) -> VM): (A) -> ViewModelProvider.NewInstanceFactory{
    return {arg: A ->
        object: ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return constructor(arg) as T
            }
        }
    }
}