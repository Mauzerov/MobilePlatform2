package com.mauzerov.mobileplatform2.entities.living

import android.util.Log
import com.mauzerov.mobileplatform2.entities.Entity
import com.mauzerov.mobileplatform2.include.Position
import com.mauzerov.mobileplatform2.values.const.GameConstants
import kotlinx.coroutines.*
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable
import kotlin.properties.Delegates

abstract class LivingEntity : Entity(), Serializable {
    abstract val leapTimeMillis: Long
    abstract val leapStrength: Float

    var isJumping: Boolean = false
        private set

    var onHealthChanged : (value: Int, max: Int) -> Unit = { _, _ ->  }

    override fun move() {
        setVelocity (null, if(!isJumping) -GameConstants.gravity else (GameConstants.gravity * leapStrength).toInt())
        super.move()
    }

    fun jump(isOnGround: (Position) -> Boolean) {
        Log.d("JUMP", isOnGround(position).toString() + " " + isJumping.toString())
        if (!isOnGround(position)) {
            Log.d("JUMP", "Not Jumping")
            return
        }
        MainScope().launch {
            isJumping = true
            delay(leapTimeMillis)
            isJumping = false
        }
    }

    open val MAX_HEALTH = BASE_HEATH
    var health: Int by Delegates.observable(BASE_HEATH) { property, oldValue, newValue ->
        Log.d("Heart", "Event")
        this.onHealthChanged(newValue, MAX_HEALTH)
    }

    fun hit(healthPoints: Int): Boolean {
        return run {
            health -= healthPoints
            health
        } < 0
    }

    fun heal(healthPoints: Int): Boolean {
        if (!canHeal()) return false
        Log.d("Nothing", MAX_HEALTH.toString())
        health = MAX_HEALTH.coerceAtMost(health + healthPoints)
        return true
    }
    fun canHeal(): Boolean {
        return health < MAX_HEALTH
    }

    override fun readExternal(`in`: ObjectInput?) {
        super.readExternal(`in`)
        `in`?.let {
            health = it.readObject() as Int
        }
    }
    override fun writeExternal(out: ObjectOutput?) {
        super.writeExternal(out)
        out?.writeObject(health)
    }

    companion object {
        const val BASE_HEATH = 10
    }
}