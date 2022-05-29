package com.mauzerov.mobileplatform2.items.consumable

import android.content.res.Resources
import android.util.Log
import android.view.MotionEvent
import com.mauzerov.mobileplatform2.items.ItemBase
import com.mauzerov.mobileplatform2.engine.threding.GameView

abstract class Food(resources: Resources): ItemBase(resources) {
    abstract val healthPoints: Int
    init {
        setSpecialActivity {

            return@setSpecialActivity true
        }
    }

//    override fun specialActivity(event: MotionEvent?, gameMap: GameView) {
//        gameMap.player.heal(healthPoints)
//        gameMap.player.items.all.remove(this)
//        removeFromEq(gameMap)
//        Log.d("Nothing", "FOOD " + gameMap.player.health.toString() + " " + gameMap.player.MAX_HEALTH)
//    }

}