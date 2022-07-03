package com.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.device.data.DeviceRepository
import com.example.feature.device.data.model.Device
import com.example.feature.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    userRepositiry: UserRepository,
    private val deviceRepository: DeviceRepository,
) : ViewModel() {

    private val _filter = MutableStateFlow(FilterDeviceType.All)
    val homePageState: StateFlow<HomePageState> by lazy {
        combine(
            userRepositiry.user,
            _filter,
            deviceRepository.devices
        ) { user, filter, devices ->
            user to devices.filterByType(filter)
        }
            .map {
                HomePageState.Content(
                    user = it.first,
                    filter = _filter.value,
                    devices = it.second
                )
            }
            .catch<HomePageState> { exception ->
                Timber.e(exception, "Error while loading data")
                emit(HomePageState.Error)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, HomePageState.Loading)
    }

    fun onFilterSelected(filter: FilterDeviceType) = viewModelScope.launch {
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
