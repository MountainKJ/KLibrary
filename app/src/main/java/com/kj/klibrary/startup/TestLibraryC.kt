package com.kj.klibrary.startup

import android.content.Context
import androidx.startup.Initializer
import com.blankj.utilcode.util.LogUtils

class TestLibraryC: Initializer<Int> {
    override fun create(context: Context): Int {
        LogUtils.e("libraryC is init")
        return 1
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}