package com.example.feature.device.data

import com.example.feature.device.data.mapper.toDevice
import com.example.feature.device.data.model.Device
import com.example.network.data.RemoteDataApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val remoteDataApi: RemoteDataApi
) {

    private val _devices = MutableStateFlow<Map<Long, Device>>(mutableMapOf())
    val devices: Flow<List<Device>> = _devices
        .map { it.values.toList() }
        .onStart { loadSavedDevices() }

    fun observeDevice(id: Long): Flow<Device> = _devices
        .map { it[id] }
        .filterNotNull()

    suspend fun deleteDevice(id: Long) = withContext(Dispatchers.IO) {
        _devices.value = _devices.value.filterKeys { it != id }
    }

    fun updateDevice(updatedDevice: Device) {
        _devices.value = _devices.value.toMutableMap().apply {
            set(updatedDevice.id, updatedDevice)
        }
    }

    private suspend fun loadSavedDevices() {
        remoteDataApi.getSavedDevices().map { it.toDevice() }
            .also { devices -> _devices.value = devices.associateBy { it.id } }
    }
}
