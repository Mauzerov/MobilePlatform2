package com.mauzerov.mobileplatform2.mvvm

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.util.Log
import android.util.Size
import androidx.core.graphics.toPointF
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.extensions.GameColor

abstract class GameBarWidget(context: Activity) {
    open lateinit var position: Point
    val positionF
        get() = position.toPointF()
    open var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.test_img_draw_canvas)
    lateinit var size: Size

    open fun draw(canvas: Canvas) {
        Log.d("First", "${position.x}")
        if (position.x < 0)
            position.x += canvas.width
        if (position.y < 0)
            position.y += canvas.height
        canvas.drawBitmap(bitmap, positionF.x, positionF.y, GameColor.paint)
    }
}