package com.example.feature.device.data.mapper

import com.example.datastore.data.model.SavedDevice
import com.example.feature.device.data.model.Device

internal fun SavedDevice.toDevice() =
    when (productType) {
        SavedDevice.Type.Light -> Device.Light(
            id = id,
            deviceName = deviceName,
            intensity = intensity ?: 0,
            mode = mode?.toDeviceMode() ?: Device.Mode.Off
        )
        SavedDevice.Type.RollerShutter -> Device.RollerShutter(
            id = id,
            deviceName = deviceName,
            position = position ?: 0
        )
        SavedDevice.Type.Heater -> Device.Heater(
            id = id,
            deviceName = deviceName,
            temperature = temperature ?: 0f,
            mode = mode?.toDeviceMode() ?: Device.Mode.Off
        )
    }

private fun SavedDevice.Mode.toDeviceMode() = when (this) {
    SavedDevice.Mode.On -> Device.Mode.On
    SavedDevice.Mode.Off -> Device.Mode.Off
}
