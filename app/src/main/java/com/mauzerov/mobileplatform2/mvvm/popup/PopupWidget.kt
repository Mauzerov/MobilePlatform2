package com.mauzerov.mobileplatform2.mvvm.popup

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.util.Log
import com.mauzerov.mobileplatform2.extensions.GameColor
import com.mauzerov.mobileplatform2.extensions.createStaticColorBitmap

open class PopupWidget {
    open val bitmap: Bitmap = createStaticColorBitmap(50, 50, Color.RED)
    open val position: Point = Point(0, 0)


    fun draw(canvas: Canvas, leftMargin: Float, topMargin: Float, rightMargin: Float, bottomMargin: Float) {
        Log.d("Popup", "${canvas.width}")
        val top = if (position.y < 0) canvas.height - bitmap.height - bottomMargin + position.y else position.y + topMargin
        val left = if (position.x < 0)  rightMargin - bitmap.width + position.x else position.x + leftMargin
        Log.d("Popup", "$left, $top")
        canvas.drawBitmap(bitmap, left, top, GameColor.paint)
    }
}