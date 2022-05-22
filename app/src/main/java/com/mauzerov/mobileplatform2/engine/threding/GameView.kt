package com.mauzerov.mobileplatform2.engine.threding

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Activity): SurfaceView(context) {
    class UpdateThread(val gameView: GameView) : Thread() {
        var isRunning = false

        companion object {
            const val RefreshInterval = 9L
        }

        @SuppressLint("WrongCall")
        override fun run() {
            while (isRunning) {
                var canvas: Canvas? = null
                try {
                    val startTime = System.currentTimeMillis()
                    canvas = gameView.holder.lockCanvas()
                    for (entity in gameView.entities) {
                        entity.move()
                    }
                    synchronized(gameView.holder) { gameView.onDraw(canvas) }
                    val duration = System.currentTimeMillis() - startTime

                    gameView.player.selectedItem?.let {
                        if (it is ItemDrawable && it.isShowed) {
                            it.drawMySelf(canvas)
                        }
                    }

                    sleep(0L.coerceAtLeast(RefreshInterval - duration))
                } finally {
                    if (canvas != null) gameView.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    private val player: Any = 0;
}