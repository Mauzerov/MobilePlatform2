package com.mauzerov.mobileplatform2.mvvm.layout

import android.content.Context
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mauzerov.mobileplatform2.MainActivity
import com.mauzerov.mobileplatform2.R
import com.mauzerov.mobileplatform2.databinding.MainMenuBinding
import com.mauzerov.mobileplatform2.mvvm.button.SaveButton

class MainMenuLayout(context: Context) : ConstraintLayout(context) {
    private lateinit var binding: MainMenuBinding

    private var closeButton: ImageButton
    private var settingButton: ImageButton
    private var main: ConstraintLayout
    private var saveLayout: LinearLayout

    private var save1button: SaveButton = SaveButton(context, this, "save1.dat")

    private var save2button: SaveButton = SaveButton(context, this, "save2.dat")

    private var save3button: SaveButton = SaveButton(context, this, "save3.dat")

    init {
        inflate(context, R.layout.main_menu, this)
        closeButton = this.findViewById(R.id.main_menu_close_button)
        settingButton = this.findViewById(R.id.main_menu_settings_button)
        settingButton.setOnClickListener {
            (context as MainActivity).setting.open()
        }
        main = this.findViewById(R.id.main_menu_main_layout)
        saveLayout = this.findViewById(R.id.savesLayout)


        saveLayout.addView(save1button)
        saveLayout.addView(save2button)
        saveLayout.addView(save3button)
    }

    fun setPlayButtonOnClickListener(runnable: (String) -> Unit) {
        save1button.apply {
            playButton.setOnClickListener { runnable(saveFileName) }
        }
        save2button.apply {
            playButton.setOnClickListener { runnable(saveFileName) }
        }
        save3button.apply {
            playButton.setOnClickListener { runnable(saveFileName) }
        }
    }
}