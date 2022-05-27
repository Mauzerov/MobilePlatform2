package com.mauzerov.mobileplatform2.engine.files

import android.content.Context
import android.util.Xml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToJsonElement
import java.io.*
import java.nio.charset.Charset

object FileSystem {
    fun readObject(context: Context, path: String) : Serializable {
        val string = File(context.filesDir, path).readText(Charset.defaultCharset())
        return Json.decodeFromString(string)
    }
    fun writeObject(context: Context, path: String, `object`: Serializable) {
        val encoded = Json.encodeToString(`object`)
        File(context.filesDir, path).writeText(encoded, Charset.defaultCharset())
    }
}