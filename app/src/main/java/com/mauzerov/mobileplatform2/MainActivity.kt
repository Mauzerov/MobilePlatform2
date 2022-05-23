package com.mauzerov.mobileplatform2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.mauzerov.mobileplatform2.adapter.controller.Dimensions
import com.mauzerov.mobileplatform2.adapter.controller.JoyStick
import com.mauzerov.mobileplatform2.adapter.controller.MovementController
import com.mauzerov.mobileplatform2.engine.threding.GameView
import com.mauzerov.mobileplatform2.mvvm.dropdown.DropdownSettings
import com.mauzerov.mobileplatform2.mvvm.dropdown.Droppable

class MainActivity : AppCompatActivity(), JoyStick.JoystickListener {
    private lateinit var settingsDroppable: Droppable
    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsDroppable = DropdownSettings(this)

//        findViewById<Button>(R.id.settings_open).setOnClickListener {
//            settingsDroppable.open()
//        }

        findViewById<View>(R.id.joystick).bringToFront()

        gameView = GameView(this)
        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(0, 0, 0, 0)
        }
        this.addContentView(gameView, panelLayout)
    }

    override fun onJoystickMoved(percent: Dimensions, id: Int) {
        gameView.onJoystickMoved(percent, id)
    }
}