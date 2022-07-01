package com.example.ui.home

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

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(FilterDeviceType.All)
    private val _homePageState = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val homePageState = _homePageState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Error while loading devices")
        _homePageState.value = HomePageState.Error
    }

    init {
        viewModelScope.launch(errorHandler) {
            combine(_filter, deviceRepository.devices) { filter, devices ->
                devices.filterByType(filter)
            }
                .onEach { _homePageState.value = HomePageState.Content(_filter.value, it) }
                .launchIn(this)
        }
    }

    fun onFilterSelected(filter: FilterDeviceType) = viewModelScope.launch(errorHandler) {
        _filter.value = filter
    }

    fun onDeleteClick(device: Device) = viewModelScope.launch {
        deviceRepository.deleteDevice(device.id)
    }

    private fun List<Device>.filterByType(filter: FilterDeviceType): List<Device> {
        val filterClass = when (filter) {
            FilterDeviceType.All -> null
            FilterDeviceType.Light -> Device.Light::class
            FilterDeviceType.RollerShutter -> Device.RollerShutter::class
            FilterDeviceType.Heater -> Device.Heater::class
        }
        return filterClass?.let { filterIsInstance(it.java) } ?: this
    }
}
