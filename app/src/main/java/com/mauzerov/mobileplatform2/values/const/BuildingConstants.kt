package com.mauzerov.mobileplatform2.values.const

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.util.Log
import com.mauzerov.mobileplatform2.engine.threding.GameView
import com.mauzerov.mobileplatform2.extensions.GameColor
import com.mauzerov.mobileplatform2.extensions.createStaticColorBitmap
import com.mauzerov.mobileplatform2.mvvm.popup.CanvasPopup
import com.mauzerov.mobileplatform2.mvvm.popup.PopupWidget
import com.mauzerov.mobileplatform2.sprites.buildings.Building
import com.mauzerov.mobileplatform2.sprites.buildings.Clickable
import com.mauzerov.mobileplatform2.sprites.buildings.MissionBuilding
import com.mauzerov.mobileplatform2.values.const.GameConstants.tileSize
import kotlin.random.Random

var random =  Random(0)

val requiredBuildings: List<Pair<Int, () -> Building>> = listOf(
    Pair(1) {
        object : MissionBuilding(), Clickable {
            override val missionId: Int = 1337
            override val roof: Bitmap =
                createStaticColorBitmap(tileSize.width, tileSize.width, Color.DKGRAY)
            override val story: Bitmap =
                createStaticColorBitmap(tileSize.width, tileSize.width, 0x50FF0000.toInt())
            override val floor: Bitmap =
                createStaticColorBitmap(tileSize.width, tileSize.width, 0x5000FF00.toInt())
            override val position: Point = Point(104, 0)
            override val size: Point = Point(2, 5)

            override fun onClick(position: Point, gameView: GameView): Boolean {
                if (!this.collides(position))
                    return false

                gameView.popup = object : CanvasPopup() {
                    init {
                        this.widgets.add(object : PopupWidget() {
                            override fun draw(
                                canvas: Canvas,
                                leftMargin: Float,
                                topMargin: Float,
                                rightMargin: Float,
                                bottomMargin: Float
                            ) {
                                canvas.drawText(
                                    "Starting Mission: $missionId",
                                    leftMargin + 160,
                                    topMargin + 100,
                                    GameColor.paint.apply {
                                        color = Color.WHITE
                                        textSize = 70F
                                    }
                                )
                            }
                        })
                    }
                }

                Log.d("Mission", "$missionId")

                return true
            }
        }
    },
    Pair(2) {
        object : MissionBuilding(), Clickable {
            override val missionId: Int = 420
            override val roof: Bitmap =
                createStaticColorBitmap(tileSize.width, tileSize.width, Color.BLUE)
            override val story: Bitmap =
                createStaticColorBitmap(tileSize.width, tileSize.width, 0xF0a29bfe.toInt())
            override val position: Point = Point(99, 0)
            override val size: Point = Point(2, 1)

            override fun onClick(position: Point, gameView: GameView): Boolean {
                if (!this.collides(position))
                    return false

                gameView.popup = object : CanvasPopup() {
                    init {
                        this.widgets.add(object : PopupWidget() {
                            override fun draw(
                                canvas: Canvas,
                                leftMargin: Float,
                                topMargin: Float,
                                rightMargin: Float,
                                bottomMargin: Float
                            ) {
                                canvas.drawText(
                                    "Starting Mission: $missionId",
                                    leftMargin + 160,
                                    topMargin + 100,
                                    GameColor.paint.apply {
                                        color = Color.WHITE
                                        textSize = 70F
                                    }
                                )
                            }
                        })
                    }
                }

                Log.d("Mission", "$missionId")

                return true
            }
        }
    },
)