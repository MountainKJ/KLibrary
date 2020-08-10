package com.kj.core.ext

import android.content.res.Resources
import android.util.TypedValue

val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Float.dpInt
    get() = dp.toInt()