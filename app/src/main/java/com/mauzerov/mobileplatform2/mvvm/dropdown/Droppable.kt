package com.mauzerov.mobileplatform2.mvvm.dropdown

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mauzerov.mobileplatform2.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("ViewConstructor")
abstract class Droppable constructor(context: Activity) :
    ConstraintLayout(context) {
    private final val animationSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    private final val animationSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)

    init {
        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            leftMargin = 0
            topMargin = 0
            rightMargin = 0
            bottomMargin = 0
        }

        this.setBackgroundColor(
            ContextCompat.getColor( context, R.color.dark_black_overlay )
        )
        (context).addContentView(this, panelLayout)

        val duration = animationSlideUp.duration
        animationSlideUp.duration = 0
        this.onClose()
        animationSlideUp.duration = duration
    }

    private final fun onClose(): Long {
        this.startAnimation(animationSlideUp)

        Handler(Looper.getMainLooper()).postDelayed(
            { this.visibility = GONE }, animationSlideUp.duration
        )
        return animationSlideUp.duration
    }

    private final fun onOpen(): Long {
        this.visibility = VISIBLE
        this.startAnimation(animationSlideDown)
        return animationSlideDown.duration
    }

    final fun open(): Long {
        return this.onOpen()
    }

    final fun close(): Long {
        return this.onClose()
    }
}