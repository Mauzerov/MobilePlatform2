package com.mauzerov.mobileplatform2.mvvm

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.util.Size
import androidx.core.graphics.toPointF
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.extensions.GameColor
import com.mauzerov.mobileplatform2.extensions.GameViewCanvasConfig
import com.mauzerov.mobileplatform2.include.Point

abstract class GameBarWidget(context: Activity) {
    open lateinit var position: Point

    val positionF
        get() = position.toPointF()
    open var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.test_img_draw_canvas)
    open lateinit var size: Size

    open fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, positionF.x, positionF.y, GameColor.paint)
    }
}