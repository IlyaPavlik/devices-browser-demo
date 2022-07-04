package com.example.datastore.data

import com.example.datastore.data.model.SavedUser
import com.example.datastore.data.source.PreferencesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SavedDataRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : SavedDataApi {

    override suspend fun getSavedUser(): SavedUser? = withContext(Dispatchers.IO) {
        preferencesDataSource.getUser()
    }

    override suspend fun saveUser(user: SavedUser) = withContext(Dispatchers.IO) {
        preferencesDataSource.saveUser(user)
    }
}
