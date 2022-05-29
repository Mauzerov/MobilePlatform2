package com.mauzerov.mobileplatform2.sprites.buildings

import android.graphics.Point

interface Clickable {
    fun onClick(position: Point): Boolean
}
interface Walkable {
    fun isNear(position: Point): Boolean
}