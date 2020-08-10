package com.kj.core.base.vm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.blankj.utilcode.util.Utils

open class BaseViewModel: AndroidViewModel(Utils.getApp()), LifecycleObserver{

    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }

    val defUI: UIChange by lazy { UIChange() }
}