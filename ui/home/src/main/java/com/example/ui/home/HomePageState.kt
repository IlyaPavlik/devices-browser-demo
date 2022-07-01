package com.example.ui.home

import com.example.feature.device.data.model.Device
import com.example.feature.user.model.User

sealed class HomePageState {
    data class Content(
        val user: User?,
        val filter: FilterDeviceType,
        val devices: List<Device>
    ) : HomePageState()

    object Loading : HomePageState()
    object Error : HomePageState()
}
