package com.mauzerov.mobileplatform2.include

import java.util.*

class FunctionStack {
    private var _stack : Stack<Runnable> = Stack()
    val size : Int get() { return _stack.size }

    fun add(function: Runnable) {
        _stack.push(function)
    }

    operator fun plusAssign(other: Runnable) {
        add(other)
    }

    fun get() : Runnable {
        return _stack.peek()
    }

    fun call() {
        _stack.pop().run()
    }

    fun remove() {
        _stack.pop()
    }

    operator fun dec() : FunctionStack{
        this.remove()
        return this
    }
}