package com.example.datastore.data

import com.example.datastore.data.model.SavedUser

interface SavedDataApi {

    /**
     * Returns saved user
     * @return saved user
     */
    suspend fun getSavedUser(): SavedUser?

    /**
     * Save the current user
     * @param user which you want to save
     */
    suspend fun saveUser(user: SavedUser)
}
