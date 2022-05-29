package com.mauzerov.mobileplatform2.items

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.mauzerov.mobileplatform2.MainActivity
import com.mauzerov.mobileplatform2.engine.threding.GameView

abstract class ItemBase(private var resources: Resources) {
    abstract val resourceIconId: Int
    abstract fun specialActivity(event: MotionEvent?, gameMap: GameView)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getIcon() : Bitmap {
        return resources.getDrawable(resourceIconId, Resources.getSystem().newTheme()).toBitmap()
    }

    fun removeFromEq(gameMap: GameView) {
//        ((gameMap.context as MainActivity).eqMenu as DropdownEq).refill(gameMap)
    }
}