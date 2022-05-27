package com.mauzerov.mobileplatform2.engine.files

interface FileLoad {
    fun <T> loadFromSaveObject(o: T)
}