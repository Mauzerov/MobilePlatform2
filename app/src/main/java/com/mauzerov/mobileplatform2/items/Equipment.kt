package com.mauzerov.mobileplatform2.items

import com.mauzerov.mobileplatform2.items.utils.Phone

class Equipment {
    var selected: ItemBase? = null
    var phone: Phone? = null
    val all: MutableList<ItemBase> = mutableListOf()
}