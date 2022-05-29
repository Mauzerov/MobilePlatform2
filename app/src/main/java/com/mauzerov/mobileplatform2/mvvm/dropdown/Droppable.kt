package com.mauzerov.mobileplatform2.mvvm.dropdown

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mauzerov.mobileplatform2.R


abstract class Droppable constructor(context: Activity) :
    ConstraintLayout(context) {
    private val animationSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    private val animationSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
    private var afterOpen : (() -> Unit)? = null

    init {
        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(0, 0, 0, 0)
        }

        this.setBackgroundColor(
            ContextCompat.getColor( context, R.color.dark_black_overlay )
        )
        @Suppress("LeakingThis")
        (context).addContentView(this, panelLayout)

        val duration = animationSlideUp.duration
        animationSlideUp.duration = 0
        this.onClose()
        animationSlideUp.duration = duration
    }

    private fun onClose(): Long {
        bringToFront()
        this.startAnimation(animationSlideUp)
        Handler(Looper.getMainLooper()).postDelayed(
            { this.visibility = GONE }, animationSlideUp.duration
        )
        return animationSlideUp.duration
    }

    private fun onOpen(): Long {
        bringToFront()
        this.visibility = VISIBLE
        afterOpen?.invoke()
        this.startAnimation(animationSlideDown)
        return animationSlideDown.duration
    }

    fun open(): Long {
        return this.onOpen()
    }

    fun close(): Long {
        return this.onClose()
    }

    fun setAfterOpenEvent(f: () -> Unit) {
        this.afterOpen = f
    }
}