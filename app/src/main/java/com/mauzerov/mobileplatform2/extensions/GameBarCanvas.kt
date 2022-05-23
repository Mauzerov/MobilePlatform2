package com.mauzerov.mobileplatform2.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget

fun Canvas.gameBarDrawWidget(widget: GameBarWidget) {
    this.drawBitmap(
        widget.bitmap,
        widget.positionF.x,
        widget.positionF.y,
        GameColor.paint
    )
}