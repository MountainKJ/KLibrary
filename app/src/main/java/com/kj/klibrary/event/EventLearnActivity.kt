package com.kj.klibrary.event

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.kj.core.base.vm.NoViewModel
import com.kj.klibrary.SupperActivity
import com.kj.klibrary.databinding.ActivityEventLearnBinding

class EventLearnActivity: SupperActivity<NoViewModel>() {

    companion object {
        fun createIntent(context: Context) = Intent(context, EventLearnActivity::class.java)
    }

    private lateinit var mBinding: ActivityEventLearnBinding
    override fun getContentViewV(): View {
        mBinding = ActivityEventLearnBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.activityEventLearnTec2.setOnClickListener{
            TouchEventUtil.doClick(javaClass)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "dispatchTouchEvent", ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "onTouchEvent", event)
        return super.onTouchEvent(event)
    }

}