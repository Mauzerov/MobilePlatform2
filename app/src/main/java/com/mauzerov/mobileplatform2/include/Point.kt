package com.mauzerov.mobileplatform2.include

import com.mauzerov.mobileplatform2.extensions.GameViewCanvasConfig

data class Point(var x: Int, var y: Int){
    init {
        if (x < 0) x += GameViewCanvasConfig.screenWidth.toInt()
        if (y < 0) y += GameViewCanvasConfig.screenHeight.toInt()
    }

    override fun toString(): String {
        return "{$x, $y}"
    }

    fun toPointF() : PointF {
        return PointF(this.x.toFloat(), this.y.toFloat())
    }

    data class PointF internal constructor(var x: Float, var y: Float)
}
