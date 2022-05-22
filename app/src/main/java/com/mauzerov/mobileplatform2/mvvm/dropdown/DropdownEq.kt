//package com.mauzerov.mobileplatform2.mvvm.dropdown
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.AttributeSet
//import android.util.Log
//import android.widget.*
//import com.mauzerov.mobileplatform2.R
//import com.mauzerov.mobileplatform2.engine.threding
//import com.mauzerov.mobileplatform2.items.ItemDrawable
//import com.mauzerov.mobileplatform2.items.consumable.Food
//
//@SuppressLint("ViewConstructor")
//class DropdownEq(context: Activity) : Droppable(context) {
//
//    init {
//        inflate(context, R.layout.dropdown_eq, this)
//
//        findViewById<ImageView>(R.id.dropdown_settings_closing_button)
//            .setOnClickListener{ this.close() }
//    }
//
//    fun refill(gameMap: GameMap) {
//        findViewById<LinearLayout>(R.id.eqItemList).removeAllViews()
//        for (item in gameMap.player.items.all) {
//            findViewById<LinearLayout>(R.id.eqItemList).addView(ImageView(context).also {
//                it.setImageBitmap(item.getIcon())
//                it.setOnClickListener {
//                    if (item !is Food) {
//                        gameMap.player.selectedItem = item
//                        gameMap.touchActions[item.resourceIconId] = { event, game ->
//                            item.specialActivity(event, game)
//                            Log.d("Nothing", "Nothing")
//                        }
//                    }
//
//                    if (item is ItemDrawable)
//                        item.isShowed = true
//                    else
//                        item.specialActivity(null, gameMap)
//                }
//            })
//        }
//    }
//}