package com.kj.klibrary.event

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton

class EventButton @JvmOverloads constructor(context: Context, attr: AttributeSet? = null)
    : AppCompatButton(context, attr) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "dispatchTouchEvent", event)
        return super.dispatchTouchEvent(event)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        TouchEventUtil.logActionMsg(javaClass, "onTouchEvent", event)
        return super.onTouchEvent(event)
    }


}