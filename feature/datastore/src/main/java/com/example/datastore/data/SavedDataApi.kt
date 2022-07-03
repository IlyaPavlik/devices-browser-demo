package com.example.datastore.data

import com.example.datastore.data.model.SavedDevice
import com.example.datastore.data.model.SavedUser

interface SavedDataApi {

    /**
     * Returns list of saved devices
     * @return list of saved devices
     */
    suspend fun getSavedDevices(): List<SavedDevice>

    /**
     * Returns saved user
     * @return saved user
     */
    suspend fun getSavedUser(): SavedUser

    /**
     * Save the current user
     * @param user which you want to save
     */
    suspend fun saveUser(user: SavedUser)
}
