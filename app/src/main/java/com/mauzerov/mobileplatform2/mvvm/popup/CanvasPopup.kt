package com.mauzerov.mobileplatform2.mvvm.popup

import android.graphics.Canvas
import android.graphics.RectF
import com.mauzerov.mobileplatform2.extensions.GameColor

open class CanvasPopup {
    val widgets: MutableList<PopupWidget> = mutableListOf()
    val height: Int = 240
    val width: Int = 420

    fun margins(width: Float, height: Float) : RectF {
        val leftMargin = ((width - this.width) / 2)
        val topMargin = ((height - 80 - this.height) / 2)
        return RectF(
            leftMargin,
            topMargin,
            leftMargin + this.width,
            topMargin + this.height,
        )
    }

    open fun draw(canvas: Canvas) {
        val margins = this.margins(canvas.width.toFloat(), canvas.height.toFloat());
        canvas.drawRect(margins, GameColor.paint.apply {
            color = 0xCC000000.toInt()
        })

        for (widget in widgets) {
            widget.draw(canvas, margins)
        }
    }
}