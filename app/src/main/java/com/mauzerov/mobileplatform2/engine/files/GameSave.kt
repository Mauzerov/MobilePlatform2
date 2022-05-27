package com.mauzerov.mobileplatform2.engine.files

import com.mauzerov.mobileplatform2.engine.files.inner.BuildingSave
import com.mauzerov.mobileplatform2.engine.files.inner.PlayerSave
import kotlinx.serialization.Serializable

@Serializable
data class GameSave(
    var playerSave: PlayerSave,
    var buildingSav: BuildingSave,
)