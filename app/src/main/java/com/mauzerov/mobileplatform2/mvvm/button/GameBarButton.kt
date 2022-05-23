package com.mauzerov.mobileplatform2.mvvm.button

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Point
import android.util.Log
import android.util.Size
import com.mauzerov.mobileplatform2.extensions.between
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget

abstract class GameBarButton(activity: Activity): GameBarWidget(activity) {
    abstract fun onClick()
    fun getPressEvent(x: Int, y: Int): Boolean {
        val condition = x.between(position.x, position.x + size.width) &&
                y.between(position.y, position.y + size.height)
        Log.d("Event", "$x, $y: $condition")
        Log.d("Event", "x: ${positionF.x} < $x < ${position.x + size.width}")
        Log.d("Event", "y: ${position.y} < $y < ${position.y + size.height}")
        if (condition) {
            onClick()
        }
        return condition
    }
}