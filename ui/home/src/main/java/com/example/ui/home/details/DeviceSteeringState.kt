package com.example.ui.home.details

import com.example.feature.device.data.model.Device

sealed class DeviceSteeringState {
    data class Content(val device: Device) : DeviceSteeringState()
    object Loading : DeviceSteeringState()
    object Error : DeviceSteeringState()
}
