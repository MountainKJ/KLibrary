package com.kj.klibrary.receiver

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import androidx.core.content.ContextCompat.getSystemService

class DPMTestsReceiver : DeviceAdminReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence? {
        return super.onDisableRequested(context, intent)
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
    }

    override fun onPasswordChanged(context: Context, intent: Intent, user: UserHandle) {
        super.onPasswordChanged(context, intent, user)
    }

    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        super.onProfileProvisioningComplete(context, intent)
//        val mDevicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        val ownAdminReceiver = ComponentName(context, DPMTestsReceiver::class.java)
//        mDevicePolicyManager.setProfileName(ownAdminReceiver, "tttttttttttttt")
//        mDevicePolicyManager.setProfileEnabled(ownAdminReceiver)
    }
}