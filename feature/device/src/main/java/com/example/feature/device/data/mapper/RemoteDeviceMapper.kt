package com.example.feature.device.data.mapper

import com.example.feature.device.data.model.Device
import com.example.network.data.model.RemoteDevice

internal fun RemoteDevice.toDevice() =
    when (productType) {
        RemoteDevice.Type.Light -> Device.Light(
            id = id,
            deviceName = deviceName,
            intensity = intensity ?: 0,
            mode = mode?.toDeviceMode() ?: Device.Mode.Off
        )
        RemoteDevice.Type.RollerShutter -> Device.RollerShutter(
            id = id,
            deviceName = deviceName,
            position = position ?: 0
        )
        RemoteDevice.Type.Heater -> Device.Heater(
            id = id,
            deviceName = deviceName,
            temperature = temperature ?: 0f,
            mode = mode?.toDeviceMode() ?: Device.Mode.Off
        )
    }

private fun RemoteDevice.Mode.toDeviceMode() = when (this) {
    RemoteDevice.Mode.On -> Device.Mode.On
    RemoteDevice.Mode.Off -> Device.Mode.Off
}
