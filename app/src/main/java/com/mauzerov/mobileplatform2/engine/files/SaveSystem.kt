package com.mauzerov.mobileplatform2.engine.files

import android.content.Context
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.io.*
import java.nio.charset.Charset

object FileSystem {
    inline fun <reified T>readObject(context: Context, path: String) : T {
        val string = File(context.filesDir, path).readText(Charset.defaultCharset())
        return Json.decodeFromString(string)
    }
    inline fun <reified T>writeObject(context: Context, path: String, `object`: T) {
        val encoded = Json.encodeToString(`object`)
        File(context.filesDir, path).writeText(encoded, Charset.defaultCharset())
    }
}