package com.mauzerov.mobileplatform2.items.consumable

import android.content.res.Resources
import com.mauzerov.mobileplatform2.R

class ECola(resources: Resources): Food(resources) {
    override val healthPoints: Int
        get() = 2
    override val resourceIconId: Int
        get() = R.drawable.tree_spruce_1
}