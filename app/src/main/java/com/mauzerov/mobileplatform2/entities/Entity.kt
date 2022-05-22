package com.mauzerov.mobileplatform2.entities

import android.util.Size

import com.mauzerov.mobileplatform2.include.Position
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

abstract class Entity: Externalizable {
    val position: Position = Position(0,0)
    var size: Size = Size(0, 0);

    open fun move() {
        position.move()
    }
    fun setVelocity(dx: Int?, dy: Int?) = position.setVelocity(dx, dy)

    override fun readExternal(`in`: ObjectInput?) {
        `in`?.let {
            val newPosition = it.readObject() as Position
            position.set(newPosition.x, newPosition.y)
        }
    }
    override fun writeExternal(out: ObjectOutput?) {
        out?.writeObject(position)
    }
}