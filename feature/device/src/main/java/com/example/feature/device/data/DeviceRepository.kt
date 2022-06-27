package com.example.feature.device.data

import com.example.datastore.data.SavedDataApi
import com.example.feature.device.data.mapper.toDevice
import com.example.feature.device.data.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val savedDataApi: SavedDataApi
) {

    suspend fun getDevices(): List<Device> = withContext(Dispatchers.IO) {
        savedDataApi.getSavedDevices().map { it.toDevice() }
    }
}
