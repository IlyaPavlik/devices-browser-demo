package com.example.network.data

import com.example.network.data.model.RemoteDevice
import com.example.network.data.model.RemoteUser

interface RemoteDataApi {

    /**
     * Returns list of devices from the server
     * @return list of devices from the server
     */
    suspend fun getSavedDevices(fromCache: Boolean = true): List<RemoteDevice>

    /**
     * Returns user from the server
     * @return user from the server
     */
    suspend fun getSavedUser(fromCache: Boolean = true): RemoteUser
}
