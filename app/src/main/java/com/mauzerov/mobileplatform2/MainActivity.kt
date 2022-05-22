package com.mauzerov.mobileplatform2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.mauzerov.mobileplatform2.engine.threding.GameView
import com.mauzerov.mobileplatform2.mvvm.dropdown.DropdownSettings
import com.mauzerov.mobileplatform2.mvvm.dropdown.Droppable

class MainActivity : AppCompatActivity() {
    private lateinit var settingsDroppable: Droppable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsDroppable = DropdownSettings(this)

        findViewById<Button>(R.id.settings_open).setOnClickListener {
            settingsDroppable.open()
        }

        val game = GameView(this)
        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(0, 0, 0, 0)
        }
        this.addContentView(game, panelLayout)
    }
}