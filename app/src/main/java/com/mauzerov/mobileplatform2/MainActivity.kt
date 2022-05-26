package com.mauzerov.mobileplatform2

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mauzerov.mobileplatform2.adapter.controller.Dimensions
import com.mauzerov.mobileplatform2.adapter.controller.JoyStick
import com.mauzerov.mobileplatform2.engine.threding.GameView
import com.mauzerov.mobileplatform2.extensions.GameViewCanvasConfig
import com.mauzerov.mobileplatform2.mvvm.dropdown.DropdownSettings
import com.mauzerov.mobileplatform2.mvvm.dropdown.Droppable
import com.mauzerov.mobileplatform2.mvvm.layout.MainMenuLayout


class MainActivity : AppCompatActivity(), JoyStick.JoystickListener {
    private lateinit var settingsDroppable: Droppable
    private lateinit var gameView: GameView
    private lateinit var mainMenu: MainMenuLayout
    private val gameViewLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(0, 0, 0, 0)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        val displayMetrics = DisplayMetrics()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsDroppable = DropdownSettings(this)

//        findViewById<Button>(R.id.settings_open).setOnClickListener {
//            settingsDroppable.open()
//        }

        closeGame()

//        gameView = GameView(this, "")

//        this.addContentView(gameView, gameViewLayout)

//        gameView.bringToFront()
//        findViewById<View>(R.id.joystick).bringToFront()
    }

    private fun closeGame(path: String? = null) {
        if (::gameView.isInitialized) {
            gameView.finished = true
            findViewById<ContentFrameLayout>(android.R.id.content).removeView(gameView)
        }

        if (::mainMenu.isInitialized)
            this.findViewById<ContentFrameLayout>(android.R.id.content).removeView(mainMenu)
        mainMenu = MainMenuLayout(this).apply {
            setPlayButtonOnClickListener {
                this@MainActivity.startGame(it)
            }
        }
        val mainMenuLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        addContentView(mainMenu, mainMenuLayoutParams)
    }

    private fun startGame(path: String) {
        if (::mainMenu.isInitialized)
            this.findViewById<ContentFrameLayout>(android.R.id.content).removeView(mainMenu)

        if (::gameView.isInitialized && !gameView.finished)
            closeGame(path)

        gameView = GameView(this, path)

        this.addContentView(gameView, gameViewLayout)
        gameView.bringToFront()
        findViewById<View>(R.id.joystick).apply {
            visibility = VISIBLE
            bringToFront()
        }
    }


    val setting: Droppable
        get() = settingsDroppable

    fun addViewOnTop(view: View, height: Int) {
        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            height
        ).apply {
            leftMargin = 0
            topMargin = 0
            rightMargin = 0
        }
        this.addContentView(view, panelLayout)
    }

    override fun onJoystickMoved(percent: Dimensions, id: Int) {
        gameView.onJoystickMoved(percent, id)
    }

    override fun onBackPressed() {
        if (::gameView.isInitialized && !gameView.finished) {
            closeGame()
            return
        }
        super.onBackPressed()
    }
}