package com.mauzerov.mobileplatform2.items.consumable

import android.content.res.Resources
import com.mauzerov.mobileplatform2.R

class Chocolate(resources: Resources) : Food(resources) {
    override val healthPoints: Int
        get() = 3
    override val resourceIconId: Int
        get() = R.drawable.ground_dirt2
}