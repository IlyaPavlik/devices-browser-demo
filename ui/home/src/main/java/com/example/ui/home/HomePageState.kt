package com.example.ui.home

import com.example.feature.device.data.model.Device

sealed class HomePageState {
    data class Content(val devices: List<Device>) : HomePageState()
    object Loading : HomePageState()
    object Error : HomePageState()
}
