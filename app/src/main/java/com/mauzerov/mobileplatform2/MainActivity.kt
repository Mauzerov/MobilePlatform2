package com.mauzerov.mobileplatform2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
    }
}