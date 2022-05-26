@file:Suppress("UNCHECKED_CAST")

package com.mauzerov.mobileplatform2.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1


fun Boolean.toInt() : Int = if (this) 1 else 0
fun Boolean.toLong() : Long = if (this) 1 else 0
fun Boolean.toShort() : Short = if (this) 1 else 0
fun Boolean.toByte() : Byte = if (this) 1 else 0

fun <T>T.between(minimumValue: T, maximumValue: T): Boolean where T : Comparable<T>, T: Number {
    return minimumValue < this && this < maximumValue
}

fun <T>T.clamp(minimumValue: T, maximumValue: T): T where T : Comparable<T>, T: Number {
    assert(minimumValue <= maximumValue)

    return if (minimumValue < this && this < maximumValue) this
    else if (this < minimumValue) minimumValue
    else maximumValue
}

fun createStaticColorBitmap(width: Int, height: Int, color: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    paint.color = color
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return bitmap
}

fun <R> Any.getValue(propertyName: String): R {
    val property = this::class.members
        .first { it.name == propertyName } as KProperty1<Any, *>
    return property.get(this) as R
}

fun <R> Any.setValue(propertyName: String, value: R) {
    val property = this::class.members.first { it.name == propertyName } as KMutableProperty<*>
    property.setter.call(this, value)
}