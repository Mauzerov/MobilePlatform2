package com.mauzerov.mobileplatform2.engine.threding

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.ButtonBarLayout
import com.mauzerov.mobileplatform2.MainActivity
import com.mauzerov.mobileplatform2.extensions.between
import com.mauzerov.mobileplatform2.extensions.createStaticColorBitmap
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget
import com.mauzerov.mobileplatform2.mvvm.button.GameBarButton
import com.mauzerov.mobileplatform2.values.const.GameConstants.RefreshInterval

@SuppressLint("ViewConstructor")
class GameBar(context: Activity, var game: GameView) : SurfaceView(context), SurfaceHolder.Callback {
    var barHeight: Int = 80

    class GameBarThread(val view: GameBar) : Thread() {
        var isRunning = false

        @SuppressLint("WrongCall")
        override fun run() {
            while (isRunning) {
                var canvas: Canvas? = null
                try {
                    val startTime = System.currentTimeMillis()
                    canvas = view.holder.lockCanvas()
                    synchronized(view.holder) { view.onDraw(canvas) }
                    val duration = System.currentTimeMillis() - startTime
                    sleep(0L.coerceAtLeast(RefreshInterval - duration))
                } finally {
                    if (canvas != null) view.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
    private val main: MainActivity = (context as MainActivity)
    private val widgets = mutableListOf<GameBarWidget>()
    private var thread = GameBarThread(this)
    private lateinit var w: Point
    init {
        Log.d("TopBar", "Init")
        holder.addCallback(this)
        widgets.add(object: GameBarButton(context) {
            override var position = Point(0, 0)
            init {
                size = Size(this@GameBar.barHeight, this@GameBar.barHeight)
                bitmap = createStaticColorBitmap(size.width, size.height, Color.RED)
            }
            override fun onClick() {
                Log.d("Event", "OnClick")
                main.setting.open()
            }
        })
        w = Point(-this@GameBar.barHeight, 0)
        //FIXME Reference Madness prevents correct event capturing \
        // ($w === it.position) return `false` though should be true
        widgets.add(object: GameBarButton(context) {
            override var position = w
            init {
                Log.d("Event", "${-barHeight}")
                size = Size(this@GameBar.barHeight, this@GameBar.barHeight)
                bitmap = createStaticColorBitmap(size.width, size.height, Color.GREEN)
            }
            override fun onClick() {
                Log.d("Event", "${position.x}")
                main.setting.open()
            }
        })

        bringToFront()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        e?.let {
            Log.d("HEIGHT", height.toString() + " " + e.x.toString())
//            Log.d("HEIGHT", e.action.toString())
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    widgets.filterIsInstance<GameBarButton>().forEach {
                        Log.d("Event", "ran ${it.position === w}")
                        return it.getPressEvent(e.x.toInt(), e.y.toInt())
                    }
//                    if (e.x.between(0f, height.toFloat())) {
////                        main.toggleSettingsMenu()
//                        return true
//                    }
//
//                    if (e.x.between((width - height - height).toFloat(), width.toFloat())) {
////                        main.toggleEqMenu()
////                        (main.eqMenu as DropdownEq).refill(game)
//                        return true
//                    }
                }
            }

        }
        return super.onTouchEvent(e)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("HEIGHT", "surfaceCreated")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("HEIGHT", "surfaceChanged")
        if (thread.state == Thread.State.TERMINATED) {
            thread = GameBarThread(this)
        }
        thread.isRunning = true
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread.isRunning = false
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (ignored : InterruptedException) {}
        }
    }
    public override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            canvas.drawColor(Color.BLACK)

            widgets.forEach {
                it.draw(canvas)
            }
            // Settings Button

/*
            canvas.drawRect(0F, 0F, height.toFloat(), height.toFloat(), Color.RED)
            drawing.RectBorderless(canvas, DisplayRect(0, 0, height, height), AlphaColor(0xFF0000))

            // Eq Button
            drawing.RectBorderless(canvas, DisplayRect(width - height - height, 0, height shl 1, height), AlphaColor(0x00FF00))

            var offset = (height shr 1) + height
            // Player Hearts
            val heartsPercent = game.player.health.toFloat() / game.player.MAX_HEALTH
            // Display Non-Colored

            var possibleHearts = HEART_DISPLAY_AMOUNT
            var possibleIndex = -1
            while (let { possibleHearts--; possibleIndex++; possibleHearts } >= 0) {
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * possibleIndex),
                    0, height shr 1, height), HEART_LEFT_NON_COLOR)
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * possibleIndex) + (height shr 1),
                    0, height shr 1, height), HEART_RIGHT_NON_COLOR)
            }

            // Display Colored
            var hearts = heartsPercent * HEART_DISPLAY_AMOUNT

            var index = -1
            //Log.d("HEIGHT1", hearts.toString())
            while (let { hearts--; index++; hearts } >= 0) {
                //Log.d("HEIGHT", hearts.toString())
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index),
                    0, height shr 1, height), HEART_LEFT_COLOR)
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index) + (height shr 1),
                    0, height shr 1, height), HEART_RIGHT_COLOR)
            }
            if (hearts >= -.5) {
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index),
                    0, height shr 1, height), HEART_LEFT_COLOR)
            }

            //Log.d("HEIGHT2", hearts.toString())

            offset += HEART_DISPLAY_AMOUNT * height

            drawing.Text(canvas, "${game.player.moneyString} €", offset + (height shr 1), height - 5, MONEY_COLOR, height.toFloat())

            //Log.d("HEIGHT", "Drawn $height $width")
*/
        }
    }

    companion object {
        const val HEART_DISPLAY_AMOUNT = 5
        @ColorInt
        val HEART_LEFT_COLOR: Int = Color.argb(255, 0xc0, 0x39, 0x2b)
        @ColorInt
        val HEART_RIGHT_COLOR: Int = Color.argb(0xFF, 0xe7, 0x4c, 0x3c)
        @ColorInt
        val HEART_LEFT_NON_COLOR: Int = Color.argb(0xFF, 0x3d, 0x3d, 0x3d)
        @ColorInt
        val HEART_RIGHT_NON_COLOR: Int = Color.argb(0xFF, 0x4b, 0x4b, 0x4b)
        @ColorInt
        val MONEY_COLOR: Int = Color.argb(0xFF, 0x6a, 0xb0, 0x4c)
    }
}