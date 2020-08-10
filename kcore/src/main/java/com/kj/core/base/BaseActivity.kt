package com.kj.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.kj.core.R
import com.kj.core.base.vm.BaseViewModel
import com.kj.core.base.vm.Message
import kotlinx.coroutines.cancel
import pub.devrel.easypermissions.EasyPermissions
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity(), EasyPermissions.PermissionCallbacks, NetWordRequest {

    abstract fun getContentView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkRegisterEventBus()
        createViewModel()
    }

    override fun onDestroy() {
        checkUnRegisterEventBus()
        super.onDestroy()
        mainScope.cancel()
    }

    //============================Init ViewModel====================================================
    protected open lateinit var mViewModel:VM
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(){
        val type = javaClass.genericSuperclass
        if(type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val vmClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProvider(this, createViewModelFactory()).get(vmClass) as VM
        }
        registerDefUIChange()
    }

    protected open fun createViewModelFactory() = ViewModelProvider.NewInstanceFactory()

    private fun registerDefUIChange() {
        mViewModel.defUI.showDialog.observe(this, Observer { showLoadingDialog() })
        mViewModel.defUI.dismissDialog.observe(this, Observer { closeLoadingDialog() })
        mViewModel.defUI.toastEvent.observe(this, Observer { ToastUtils.showShort(it) })
        mViewModel.defUI.msgEvent.observe(this, Observer {
            handMessageEvent(it)
        })
    }

    protected open fun handMessageEvent(msg: Message) {}

    private var mDialog: MaterialDialog? = null
    protected open fun showLoadingDialog() {
        if (mDialog == null) {
            mDialog = MaterialDialog(this, CustomDialogBehavior)
                .cancelable(false)
                .cornerRadius(8f)
                .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
                .lifecycleOwner(this)
                .maxWidth(R.dimen.dialog_width)
        }
        mDialog?.show()
    }

    protected open fun closeLoadingDialog() {
        mDialog?.run {
            if (isShowing) dismiss()
        }
    }

    //============================Event Bus Method==================================================
    protected open fun useEventBus() = false

    private fun checkRegisterEventBus() {
        if(useEventBus()){
            BusUtil.get().register(this)
        }
    }

    private fun checkUnRegisterEventBus() {
        if(useEventBus()) {
            BusUtil.get().unRegister(this)
        }
    }


    //============================EasyPermissions Override Method===================================
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }
}