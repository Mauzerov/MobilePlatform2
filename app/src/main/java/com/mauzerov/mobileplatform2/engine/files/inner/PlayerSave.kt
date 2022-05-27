package com.mauzerov.mobileplatform2.engine.files.inner

import kotlinx.serialization.Serializable
import kotlin.properties.Delegates

@Serializable
data class PlayerSave(
    var positionX:Int,
    var positionY:Int,
    var health:Int,
    var money:Long,
)