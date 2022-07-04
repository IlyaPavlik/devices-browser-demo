package com.example.network.data

import com.example.network.data.model.RemoteData
import com.example.network.data.model.RemoteDevice
import com.example.network.data.model.RemoteUser
import com.example.network.data.source.DataCacheSource
import com.example.network.data.source.DataRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DataRepository @Inject constructor(
    private val dataRemoteSource: DataRemoteSource,
    private val dataCacheSource: DataCacheSource
) : RemoteDataApi {

    override suspend fun getSavedDevices(fromCache: Boolean): List<RemoteDevice> =
        withContext(Dispatchers.IO) {
            if (fromCache) {
                (dataCacheSource.getData() ?: loadRemoteData()).devices
            } else {
                loadRemoteData().devices
            }
        }

    override suspend fun getSavedUser(fromCache: Boolean): RemoteUser =
        withContext(Dispatchers.IO) {
            if (fromCache) {
                (dataCacheSource.getData() ?: loadRemoteData()).user
            } else {
                loadRemoteData().user
            }
        }

    private suspend fun loadRemoteData(): RemoteData {
        return dataRemoteSource.getData().also {
            dataCacheSource.saveData(it)
        }
    }
}
