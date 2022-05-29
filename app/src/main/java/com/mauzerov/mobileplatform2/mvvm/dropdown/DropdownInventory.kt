package com.mauzerov.mobileplatform2.mvvm.dropdown

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.*
import com.mauzerov.mobileplatform2.items.ItemDrawable
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.engine.threding.GameView
import com.mauzerov.mobileplatform2.items.Equipment
import com.mauzerov.mobileplatform2.items.consumable.Food

@SuppressLint("ViewConstructor")
class DropdownInventory(context: Activity) : Droppable(context) {

    init {
        inflate(context, R.layout.dropdown_eq, this)

        findViewById<ImageView>(R.id.dropdown_settings_closing_button)
            .setOnClickListener{ this.close() }
    }

    fun refill(eq: Equipment) {
        findViewById<LinearLayout>(R.id.eqItemList).removeAllViews()
        for (item in eq.all) {
            findViewById<LinearLayout>(R.id.eqItemList).addView(ImageView(context).also { image ->
                image.setImageBitmap(item.getIcon())
                image.setOnClickListener {
                    if (item !is Food) {
                        eq.selected = item
//                        gameMap.touchActions[item.resourceIconId] = { event, game ->
//                            item.specialActivity(event, game)
//                            Log.d("Nothing", "Nothing")
//                        }
                    }

                    if (item is ItemDrawable)
                        item.isShowed = true
                    else {
                        if (item.callSpecialActivity()) {
                            eq.selected = null
                            eq.all.remove(item)
                            refill(eq)
                        }
                    }
                }
            })
        }
    }
}