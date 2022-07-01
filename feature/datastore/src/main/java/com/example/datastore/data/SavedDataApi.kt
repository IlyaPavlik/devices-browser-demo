package com.example.datastore.data

import com.example.datastore.data.model.SavedDevice
import com.example.datastore.data.model.SavedUser

interface SavedDataApi {

    suspend fun getSavedDevices(): List<SavedDevice>

    suspend fun getSavedUser(): SavedUser

    suspend fun saveUser(user: SavedUser)
}
