package com.mauzerov.mobileplatform2.sprites.buildings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import com.mauzerov.mobileplatform2.extensions.between
import com.mauzerov.mobileplatform2.extensions.between2
import com.mauzerov.mobileplatform2.extensions.gameDrawBitmap
import com.mauzerov.mobileplatform2.values.const.GameConstants.heightMap
import com.mauzerov.mobileplatform2.values.const.GameConstants.tileSize

abstract class Building {
    abstract val roof: Bitmap
    abstract val story: Bitmap
    open val floor: Bitmap? = null

    // Point(x=index on height map; y=unused)
    abstract val position: Point
    // Size(x=width in amount of bitmaps, y=amount of stories)
    abstract val size: Point

    fun collides(x: Int) : Boolean {
        return x.between2(position.x, position.x + size.x - 1)
    }

    fun fits(left: Int, right: Int): Boolean {
        return position.x.between2(left, right)
    }

    fun draw(canvas: Canvas, left: Int) {
        var drawX = (position.x - left) * tileSize.width
        for (i in 0 until size.x) {
            var offsetFromGround = (heightMap[position.x].actual) * tileSize.height + tileSize.width
            floor?.let {
                canvas.gameDrawBitmap(it, drawX, offsetFromGround)
                offsetFromGround += tileSize.width
            }
            repeat(size.y) {
                canvas.gameDrawBitmap(story, drawX, offsetFromGround)
                offsetFromGround += tileSize.width
            }
            canvas.gameDrawBitmap(roof, drawX, offsetFromGround)

            drawX += tileSize.width
        }
    }
}