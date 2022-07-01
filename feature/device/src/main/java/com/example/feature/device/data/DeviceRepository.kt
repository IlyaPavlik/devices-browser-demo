package com.example.feature.device.data

import com.example.datastore.data.SavedDataApi
import com.example.feature.device.data.mapper.toDevice
import com.example.feature.device.data.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val savedDataApi: SavedDataApi
) {

    private val _devices = MutableStateFlow<Map<Long, Device>>(mutableMapOf())
    val devices: Flow<List<Device>> = _devices
        .map { it.values.toList() }
        .onStart { loadSavedDevices() }

    suspend fun getDevice(id: Long): Device? = withContext(Dispatchers.IO) {
        _devices.value[id]
    }

    suspend fun deleteDevice(id: Long) = withContext(Dispatchers.IO) {
        _devices.value = _devices.value.filterKeys { it != id }
    }

    private suspend fun loadSavedDevices(): List<Device> {
        return savedDataApi.getSavedDevices().map { it.toDevice() }
            .also { devices -> _devices.value = devices.associateBy { it.id } }
    }
}
