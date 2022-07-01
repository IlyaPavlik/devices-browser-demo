package com.example.ui.home.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.device.data.DeviceRepository
import com.example.feature.device.data.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val _deviceState = MutableStateFlow<DeviceSteeringState>(DeviceSteeringState.Loading)
    val deviceState = _deviceState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Error while loading device")
        _deviceState.value = DeviceSteeringState.Error
    }

    init {
        viewModelScope.launch(errorHandler) {
            deviceRepository.observeDevice(initialDeviceId)
                .onEach { device -> _deviceState.value = DeviceSteeringState.Content(device) }
                .launchIn(this)
        }
    }

    fun onToggleClick() {
        _deviceState.getDevice()?.let { device ->
            val updatedDevice = when (device) {
                is Device.Heater -> device.copy(mode = device.mode.toggle())
                is Device.Light -> device.copy(mode = device.mode.toggle())
                is Device.RollerShutter -> null
            }
            updatedDevice?.let { deviceRepository.updateDevice(updatedDevice) }
        }
    }

    fun onSliderChange(value: Float) {
        _deviceState.getDevice()?.let { device ->
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
