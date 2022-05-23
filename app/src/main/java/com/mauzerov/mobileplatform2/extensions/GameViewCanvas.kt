package com.mauzerov.mobileplatform2.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorLong

// (player.position.x % tileSize.width)
object GameColor {
    val paint = Paint().apply {
        this.color = 0x0
        this.alpha = 0xFF
        this.setAlpha(0xFF)
    }

    fun getAlpha(color: Long): Int {
        return ((color and 0xFF000000) shr (3 * 8)).toInt()
    }
    fun noAlpha(color: Long): Int {
        return (color and 0x00FFFFFF).toInt()
    }
}

object GameViewCanvasConfig {
    var screenHeight: Float = 0F
    var screenOffsetX: Float = 0F
    var screenOffsetY: Float = 0F
}

fun Canvas.gameDrawBitmap(bitmap: Bitmap, x: Int, y: Int) {
    this.drawBitmap(bitmap,
        x.toFloat() - GameViewCanvasConfig.screenOffsetX,
        GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY,
        Paint())
}

fun Canvas.gameDrawText(text: String, x: Int, y: Int, color: Long, fontSize: Float = 20F) {
    this.drawText(text,
        x.toFloat() - GameViewCanvasConfig.screenOffsetX,
        GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY,
        GameColor.paint.apply {
            this.alpha = GameColor.getAlpha(color)
            this.color = GameColor.noAlpha(color)
        })
}

fun Canvas.gameDrawRect(x: Int, y: Int, w: Int, h: Int, @ColorInt color: Int) {
    Log.d("idk", "${GameViewCanvasConfig.screenHeight} ${x.toFloat() - GameViewCanvasConfig.screenOffsetX} ${GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY} $w $h")
    val left = x.toFloat() - GameViewCanvasConfig.screenOffsetX
    val top = GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY
    val right = left + w.toFloat()
    val bottom = top + h.toFloat()
    Log.d("Values", "x=$x; y=$y; w=$w; h=$h")
    Log.d("Values", "left=$left; top=$top right=$right; bottom=$bottom")
    Log.d("Values", "sH=${GameViewCanvasConfig.screenHeight}")
    Log.d("Values", "offset=${GameViewCanvasConfig.screenOffsetX} ${GameViewCanvasConfig.screenOffsetY}")
    Log.d("Values", "alpha=${Color.alpha(color)}")
    this.drawRect(left, top,
        right, bottom, GameColor.paint.apply {
            this.alpha = Color.alpha(color)
            this.color = color
        }
    )
    Log.d("Color", "${GameColor.paint.alpha}")
}