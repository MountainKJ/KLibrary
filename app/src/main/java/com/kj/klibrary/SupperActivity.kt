package com.kj.klibrary

import android.os.Bundle
import android.view.View
import com.kj.core.base.BaseActivity
import com.kj.core.base.vm.BaseViewModel

abstract class SupperActivity<VM: BaseViewModel> : BaseActivity<VM>(){
    override fun getContentView(): Int = -1

    abstract fun getContentViewV(): View

    protected open fun doOnCreate(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewV())
        doOnCreate(savedInstanceState)
    }
}