package com.mauzerov.mobileplatform2.mvvm.other

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget

class GameBarHeart(var context: Activity): GameBarWidget(context) {

    enum class State {
        FULL, HALF, NONE
    }
    var state: State = State.NONE


    override var bitmap: Bitmap
        get() = when(this.state) {
            State.FULL -> BitmapFactory.decodeResource(context.resources, R.drawable.game_bar_heart_full)
            State.HALF -> BitmapFactory.decodeResource(context.resources, R.drawable.game_bar_heart_half)
            State.NONE -> BitmapFactory.decodeResource(context.resources, R.drawable.game_bar_heart_none)
        }
        set(_) {}
}