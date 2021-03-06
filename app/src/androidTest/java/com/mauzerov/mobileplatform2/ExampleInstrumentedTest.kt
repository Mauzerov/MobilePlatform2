package com.mauzerov.mobileplatform2

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import android.graphics.Point

var s = ""

fun println(z: String) = {
    ->
    s+=z+"\n"
}

fun <T>T.between(minimumValue: T, maximumValue: T): Boolean where T : Comparable<T>, T: Number {
    return minimumValue < this && this < maximumValue
}

abstract class BaseAbc {
    open lateinit var point: Point
    open lateinit var size: Point

    fun fix() {
        if (point.x < 0)
            point.x += 69
        if (point.y < 0)
            point.y += 80
    }
}

abstract class Clickable: BaseAbc() {
    abstract fun onClick()

    fun clickIf(x: Int, y: Int) : Boolean {
        println("DEBUG: ${point}")
        if (point.x.between(-100, 100) && point.y in -20..0) {
            this.onClick()
        }
        return true
    }
}

fun main(args: Array<String>) {
    val array = mutableListOf<BaseAbc>()

    array.add(object : Clickable() {
        override var point = Point(-100, 0)
        init {
            println("1. Ini")
            size = Point(78, 4343)
        }
        override fun onClick() {
            println("Cliced1 $point")
        }
    })
    array.add(object : Clickable() {
        override var point = Point(99, 0)
        init {
            println("2. Ini")
            size = Point(7483, 43)
        }
        override fun onClick() {
            println("Cliced2 $point")
        }
    })

    object : Thread() {
        override fun run() {
            array[0].point.y -= 100
        }
    }.run()
    array.forEach {
        it.fix()
    }
    array.filterIsInstance<Clickable>().forEach {
        if (it.clickIf(-31, 0))
            it.onClick()
        it.size
    }
}


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.mauzerov.mobileplatform2", appContext.packageName)




        main(arrayOf())

        assertEquals("1. Ini\n" +
                "2. Ini\n" +
                "DEBUG: java.awt.Point[x=-31,y=-20]\n" +
                "Cliced1 java.awt.Point[x=-31,y=-20]\n" +
                "Cliced1 java.awt.Point[x=-31,y=-20]\n" +
                "DEBUG: java.awt.Point[x=99,y=0]\n" +
                "Cliced2 java.awt.Point[x=99,y=0]\n" +
                "Cliced2 java.awt.Point[x=99,y=0]\n", s)
    }
}