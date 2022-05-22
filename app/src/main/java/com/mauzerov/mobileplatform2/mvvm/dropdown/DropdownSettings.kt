package com.mauzerov.mobileplatform2.mvvm.dropdown

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.*
import com.mauzerov.mobileplatform2.R

@SuppressLint("ViewConstructor")
class DropdownSettings(context: Activity) : Droppable(context) {
    private var locationButton: Button
    private var locationCheckBox: CheckBox
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var locationSwitch: Switch

    init {
        inflate(context, R.layout.dropdown_settings, this)

        locationButton = findViewById(R.id.dropdown_custom_location_button)
        locationCheckBox = findViewById(R.id.dropdown_custom_location_checkbox)
        locationSwitch = findViewById(R.id.dropdown_settings_controller_side_switch)

        findViewById<ImageView>(R.id.dropdown_settings_closing_button)
            .setOnClickListener{ this.close() }

        locationCheckBox.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) {
                locationButton.isEnabled = true
                locationSwitch.isEnabled = false
            } else {
                locationButton.isEnabled = false
                locationSwitch.isEnabled = true
            }
        }
    }
}