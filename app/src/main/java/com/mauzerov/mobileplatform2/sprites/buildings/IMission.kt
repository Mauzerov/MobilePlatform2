package com.mauzerov.mobileplatform2.sprites.buildings

import android.graphics.Point
import com.mauzerov.mobileplatform2.engine.threding.GameView

interface Clickable {
    fun onClick(position: Point, gameView: GameView): Boolean
}
interface Walkable {
    fun isNear(position: Point): Boolean
}