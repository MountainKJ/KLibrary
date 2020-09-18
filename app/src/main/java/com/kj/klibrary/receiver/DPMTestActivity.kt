package com.kj.klibrary.receiver

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.kj.core.base.vm.NoViewModel
import com.kj.core.ext.showToast
import com.kj.klibrary.SupperActivity
import com.kj.klibrary.databinding.ActivityDpmTestBinding

class DPMTestActivity : SupperActivity<NoViewModel>(){
    companion object{
        fun createIntent(context: Context) = Intent(context, DPMTestActivity::class.java)
    }

    private lateinit var mBinding: ActivityDpmTestBinding
    override fun getContentViewV(): View {
        return ActivityDpmTestBinding.inflate(layoutInflater)
            .also { mBinding = it }.root
    }

    private val mDevicePolicyManager by lazy {
        getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }

    private val ownAdminReceiver by lazy {
        ComponentName(this, DPMTestsReceiver::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.dpmTestRequestAuthBtn.setOnClickListener {
//            requestLockAdmins()
//            provisionManagedProfile()
        }
        mBinding.dpmTestHideXsBtn.setOnClickListener {
            mDevicePolicyManager.setApplicationHidden(ownAdminReceiver, "com.sm.xsxs", true)
        }

        mBinding.dpmTestShowXsBtn.setOnClickListener {
            mDevicePolicyManager.setApplicationHidden(ownAdminReceiver, "com.sm.xsxs", false);
        }
        notifyInfoWidget()
    }

    private fun notifyInfoWidget() {
//        val authState = checkActive()
        val authState = mDevicePolicyManager.isProfileOwnerApp(packageName)
        mBinding.dpmTestInfoTv.text = "授权状态: $authState"
    }

    private fun checkActive() = mDevicePolicyManager.isAdminActive(ownAdminReceiver)

    private fun requestLockAdmins() {
        if(checkActive()) {
            showToast("已经获得设备管理器授权")
            return
        }
        val mIntent = Intent().apply {
            action = DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, ownAdminReceiver)

        }
        startActivityForResult(mIntent, 1001)
    }

    private fun provisionManagedProfile() {
        val mIntent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE).apply {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME, packageName)
            }else {
                putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, ownAdminReceiver)
            }
        }

        if(mIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(mIntent, 1002)
        }else {
            showToast("Device provisioning is not enabled, Stopping")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) return
        when(requestCode) {
            1001 -> {
                notifyInfoWidget()
            }
        }
    }
}