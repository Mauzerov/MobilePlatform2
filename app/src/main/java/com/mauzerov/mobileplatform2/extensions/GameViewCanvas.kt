package com.mauzerov.mobileplatform2.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

// (player.position.x % tileSize.width)
object GameColor {
    fun getAlpha(color: Long): Int {
        return ((color and 0xFF000000) shr 3 * 8).toInt()
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
    val paint = Paint().apply {
        this.alpha = GameColor.getAlpha(color)
        this.color = GameColor.noAlpha(color)
    }

    this.drawText(text,
        x.toFloat() - GameViewCanvasConfig.screenOffsetX,
        GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY,
        paint)
}

fun Canvas.gameDrawRect(x: Int, y: Int, w: Int, h: Int, color: Long) {
    this.drawRect(x.toFloat() - GameViewCanvasConfig.screenOffsetX,
        GameViewCanvasConfig.screenHeight - y.toFloat() - GameViewCanvasConfig.screenOffsetY,
        w.toFloat(), h.toFloat(), Paint().apply {
            this.alpha = GameColor.getAlpha(color)
            this.color = GameColor.noAlpha(color)
        }
    )
}