package com.mauzerov.mobileplatform2.items.consumable

import android.content.res.Resources
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.items.consumable.Food

class Sprouty(resources: Resources) : Food(resources) {
    override val healthPoints: Int
        get() = 2
    override val resourceIconId: Int
        get() = R.drawable.ground_concrete
}