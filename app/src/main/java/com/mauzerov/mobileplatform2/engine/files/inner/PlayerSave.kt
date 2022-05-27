package com.mauzerov.mobileplatform2.engine.files.inner

import kotlinx.serialization.Serializable

@Serializable
abstract class PlayerSave {
    abstract var positionX: Int
    abstract var positionY: Int
    abstract var health: Int
    abstract var money: Long
}