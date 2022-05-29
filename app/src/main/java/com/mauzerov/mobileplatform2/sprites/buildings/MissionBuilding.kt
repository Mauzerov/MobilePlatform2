package com.mauzerov.mobileplatform2.sprites.buildings

import com.mauzerov.mobileplatform2.include.MissionId

abstract class MissionBuilding: Building() {
    @MissionId
    abstract val missionId: Int
}