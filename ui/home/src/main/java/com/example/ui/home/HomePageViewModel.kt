package com.example.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.device.data.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _homePageState = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val homePageState = _homePageState.asStateFlow()

    init {
        Timber.d("!!! > HomePageViewModel")
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "Error while loading devices")
            _homePageState.value = HomePageState.Error
        }
        viewModelScope.launch(errorHandler) {
            Timber.d("!!! > Start loading")
            val devices = deviceRepository.getDevices()
            Timber.d("!!! loaded: $devices")
            _homePageState.value = HomePageState.Content(devices)
        }
    }

}
