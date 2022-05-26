package com.mauzerov.mobileplatform2.include

import android.util.Log
import com.mauzerov.mobileplatform2.extensions.toInt
import com.mauzerov.mobileplatform2.values.const.GameConstants
import java.io.Serializable

data class Position(var x:Int, var y: Int) : Serializable {
    private var verticalVelocity = 0 // |
    private var horizontalVelocity = 0 // -

    fun set(x: Int, y: Int): Position {
        this.x = x; this.y = y
        return this
    }

    fun setVelocity(dx: Int?, dy: Int?) {
        dy?.let { verticalVelocity = it }
        dx?.let { horizontalVelocity = it }
    }

    fun move() {
        x += horizontalVelocity / 1.coerceAtLeast(2 * (x / GameConstants.tileSize.width !in 0 .. GameConstants.mapSize + 1).toInt())
        y += verticalVelocity
    }
}