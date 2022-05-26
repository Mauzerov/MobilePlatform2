package com.mauzerov.mobileplatform2.engine.threding

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.ColorInt
import com.mauzerov.mobileplatform2.MainActivity
import com.mauzerov.mobileplatform2.extensions.GameColor
import com.mauzerov.mobileplatform2.extensions.GameViewCanvasConfig
import com.mauzerov.mobileplatform2.extensions.createStaticColorBitmap
import com.mauzerov.mobileplatform2.mvvm.GameBarWidget
import com.mauzerov.mobileplatform2.mvvm.button.GameBarButton
import com.mauzerov.mobileplatform2.values.const.GameConstants.RefreshInterval
import com.mauzerov.mobileplatform2.include.Point
import com.mauzerov.mobileplatform2.mvvm.other.GameBarHeartRow

@SuppressLint("ViewConstructor")
class GameBar(var context: Activity, var game: GameView) { //: SurfaceView(context), SurfaceHolder.Callback {
    var barHeight = 80
    private var heartRow: GameBarHeartRow

    private val main: MainActivity = (context as MainActivity)
    private val widgets = mutableListOf<GameBarWidget>()

    init {
        widgets.add(object: GameBarButton(context) {
            override var position = Point(0, 0)
            init {
                size = Size(this@GameBar.barHeight, this@GameBar.barHeight)
                bitmap = createStaticColorBitmap(size.width, size.height, Color.RED)
            }
            override fun onClick() {
                main.setting.open()
            }
        })
        widgets.add(object: GameBarButton(context) {
            override var position = Point(-(2*this@GameBar.barHeight), 0)
            override var size = Size(2*this@GameBar.barHeight, this@GameBar.barHeight)
            override var bitmap = createStaticColorBitmap(size.width, size.height, Color.GREEN)

            override fun onClick() {
                main.setting.open()
            }
        })

        widgets.add(object: GameBarButton(context) {
            override var position = Point(this@GameBar.barHeight, 0)
            override var size = Size(this@GameBar.barHeight, this@GameBar.barHeight)
            override var bitmap = createStaticColorBitmap(size.width, size.height, Color.BLUE)

            override fun onClick() {
                game.player.hit(1)
            }
        })
        widgets.add(object: GameBarButton(context) {
            override var position = Point(2 * this@GameBar.barHeight, 0)
            override var size = Size(this@GameBar.barHeight, this@GameBar.barHeight)
            override var bitmap = createStaticColorBitmap(size.width, size.height, Color.YELLOW)

            override fun onClick() {
                game.player.heal(2)
            }
        })

        heartRow = GameBarHeartRow(context).apply {
            size = Size(5*this@GameBar.barHeight, this@GameBar.barHeight)
            position = Point(240, 0)
        }
        widgets.add(heartRow)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onTouchEvent(e: MotionEvent?): Boolean {
        e?.let {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    return widgets.filterIsInstance<GameBarButton>().any { btn ->
                        btn.getPressEvent(e.x.toInt(), e.y.toInt())
                    }
                }
                else -> return false
            }
        }
        return false
    }

    fun updateHearts(value: Int, max: Int, count: Int) {
        this.heartRow.updateHearts(value, max, count)
    }

    fun onDraw(canvas: Canvas) {
        canvas.drawRect(0F, 0F,
            GameViewCanvasConfig.screenWidth, barHeight.toFloat(),
            GameColor.paint.apply {
            color = Color.BLACK
        })

        widgets.forEach {
            it.draw(canvas)
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