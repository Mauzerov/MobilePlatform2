package com.mauzerov.mobileplatform2.engine.files

import com.mauzerov.mobileplatform2.engine.files.inner.BuildingSave
import com.mauzerov.mobileplatform2.engine.files.inner.PlayerSave
import kotlinx.serialization.Serializable

@Serializable
abstract class GameSave {
    abstract var playerSave: PlayerSave
    abstract var buildingSav: BuildingSave
}