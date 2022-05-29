package com.mauzerov.mobileplatform2.entities.living

import android.util.Size
import com.mauzerov.mobileplatform2.engine.files.FileLoad
import com.mauzerov.mobileplatform2.engine.files.inner.PlayerSave
import com.mauzerov.mobileplatform2.items.Equipment
import com.mauzerov.mobileplatform2.items.ItemBase

// TODO: Make Eq Savable 
class Player(x: Int, y: Int, w: Int, h: Int) : LivingEntity(), FileLoad {
    override val leapTimeMillis: Long
        get() = 300
    override val leapStrength: Float
        get() = 2F
    override val MAX_HEALTH: Int
        get() = 20
    var money = 0L
    var items: Equipment = Equipment()
    var selectedItem: ItemBase? = null

    val moneyString: String
        get() = money.toString()

    init {
        position.set(x, y)
        size = Size(w, h)

    }

    override fun <T> loadFromSaveObject(o: T) {
        (o as PlayerSave).let {
            position.x = it.positionX
            position.y = it.positionY
            money = it.money
            health = it.health
        }
    }

//    fun setFromSaveData(saveData: PlayerSaveData) {
//        position.set(saveData.position.x, saveData.position.y)
//        health = saveData.heath
//    }
}