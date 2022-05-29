package com.mauzerov.mobileplatform2.items.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.mauzerov.mobileplatform2.MainActivity
import com.mauzerov.mobileplatform2.items.ItemBase
import com.mauzerov.mobileplatform2.items.ItemDrawable
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.engine.threding.GameView

class Phone(resources: Resources) : ItemBase(resources), ItemDrawable {
    override var isShowed: Boolean = false
    var isUp = false
        private set
    override val resourceIconId: Int
        get() = R.drawable.test_img_draw_canvas

    init {
        setSpecialActivity {
            isUp = false; return@setSpecialActivity true;
        }
    }

    override fun drawMySelf(canvas: Canvas) {
        getIcon().apply {
            canvas.drawBitmap(
                this,
                (canvas.width - width).toFloat(),
                (canvas.height - height).toFloat(),
                Paint()
            )
        }
    }
}