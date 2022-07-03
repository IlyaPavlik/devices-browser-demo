package com.example.ui.home.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.device.data.DeviceRepository
import com.example.feature.device.data.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class DeviceSteeringViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    private val initialDeviceId: Long = savedStateHandle[ARG_DEVICE_ID]
        ?: error("Device id is not defined")

    val deviceState by lazy {
        deviceRepository.observeDevice(initialDeviceId)
            .map { device -> DeviceSteeringState.Content(device) }
            .catch<DeviceSteeringState> { exception ->
                Timber.e(exception, "Error while loading device")
                emit(DeviceSteeringState.Error)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, DeviceSteeringState.Loading)
    }

    fun onToggleClick() {
        deviceState.getDevice()?.let { device ->
            val updatedDevice = when (device) {
                is Device.Heater -> device.copy(mode = device.mode.toggle())
                is Device.Light -> device.copy(mode = device.mode.toggle())
                is Device.RollerShutter -> null
            }
            updatedDevice?.let { deviceRepository.updateDevice(updatedDevice) }
        }
    }

    fun onSliderChange(value: Float) {
        deviceState.getDevice()?.let { device ->
            val updatedDevice = when (device) {
                is Device.Heater -> device.copy(temperature = value.roundToHalf())
                is Device.Light -> device.copy(intensity = value.roundToInt())
                is Device.RollerShutter -> device.copy(position = value.roundToInt())
            }
            deviceRepository.updateDevice(updatedDevice)
        }
    }

    private fun Device.Mode.toggle(): Device.Mode = when (this) {
        Device.Mode.On -> Device.Mode.Off
        Device.Mode.Off -> Device.Mode.On
    }

    private fun StateFlow<DeviceSteeringState>.getDevice(): Device? =
        (value as? DeviceSteeringState.Content)?.device

    private fun Float.roundToHalf() = (this * 2).roundToInt() / 2f

    companion object {
        private const val ARG_DEVICE_ID = "deviceId"
    }
}
