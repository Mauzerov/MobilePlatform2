package com.mauzerov.mobileplatform2.mvvm.popup

import android.graphics.Canvas
import com.mauzerov.mobileplatform2.extensions.GameColor

open class CanvasPopup {
    val widgets: MutableList<PopupWidget> = mutableListOf()
    open val marginInline = 400
    open val marginBlock = 200

    open fun draw(canvas: Canvas) {
        val leftMargin = marginInline.toFloat()
        val topMargin = marginBlock.toFloat()
        val rightMargin = canvas.width - marginInline.toFloat()
        val bottomMargin = canvas.height - marginBlock.toFloat()

        canvas.drawRect(leftMargin, topMargin, rightMargin, bottomMargin, GameColor.paint.apply {
            color = 0xCC000000.toInt()
        })

        for (widget in widgets) {
            widget.draw(canvas, leftMargin, topMargin, rightMargin, bottomMargin)
        }
    }
}