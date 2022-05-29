package com.mauzerov.mobileplatform2.items

import android.graphics.Canvas

interface ItemDrawable {
    fun drawMySelf(canvas: Canvas)
    var isShowed: Boolean
}