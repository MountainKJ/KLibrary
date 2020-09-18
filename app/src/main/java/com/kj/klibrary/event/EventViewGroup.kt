package com.kj.klibrary.event

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class EventViewGroup @JvmOverloads constructor(context: Context, attr: AttributeSet? = null): LinearLayout(context, attr) {
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "dispatchTouchEvent", ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "onTouchEvent", event)
        return super.onTouchEvent(event)
    }
}