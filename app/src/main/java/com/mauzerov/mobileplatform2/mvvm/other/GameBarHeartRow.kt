package com.mauzerov.mobileplatform2.mvvm.other

import android.app.Activity
import android.graphics.Canvas
import android.util.Log
import android.util.Size
import com.mauzerov.mobileplatform2.include.NoUpdate
import com.mauzerov.mobileplatform2.include.Point
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget
import java.lang.Math.round
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class GameBarHeartRow(var context: Activity): GameBarWidget(context), NoUpdate {
    private var _hearts = mutableListOf<GameBarHeart>()
    var hearts = 0
        private set

    fun updateHearts(value: Int, max: Int, count: Int) {
        if (hearts != count) {
            _hearts.clear()
            for (i in 1..count)
                _hearts.add(GameBarHeart(context))
        }
        hearts = count

        var `val` = (value.toFloat() / max.toFloat() * count.toFloat() * 2).roundToInt()
        _hearts.forEachIndexed { index, it ->
            it.size = Size(this.size.height, this.size.height)
            it.position = Point(this.position.x + (index * it.size.width), 0)
            it.state = when {
                `val` > 1 -> GameBarHeart.State.FULL
                `val` == 1 ->  GameBarHeart.State.HALF
                else -> GameBarHeart.State.NONE
            }
            `val` -= 2
        }

        Log.d("Heart", "$value, $max, $count")
        Log.d("Heart", "${(value.toFloat() / max.toFloat() * count.toFloat() * 2).roundToInt()}")

    }

    override fun draw(canvas: Canvas) {
        _hearts.forEach {
            it.draw(canvas)
        }
    }
}