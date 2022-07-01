package com.example.ui.home

import androidx.annotation.StringRes

enum class FilterDeviceType(@StringRes val resId: Int) {
    All(R.string.filter_all),
    Light(R.string.filter_light),
    RollerShutter(R.string.filter_roller_shutter),
    Heater(R.string.filter_heater)
}
