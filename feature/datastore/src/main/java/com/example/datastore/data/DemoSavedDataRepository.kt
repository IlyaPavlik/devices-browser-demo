package com.example.datastore.data

import com.example.datastore.data.model.DemoData
import com.example.datastore.data.model.SavedDevice
import com.example.datastore.data.model.SavedUser
import com.example.datastore.data.source.PreferencesDataSource
import com.example.datastore.data.source.RawDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DemoSavedDataRepository @Inject constructor(
    private val rawDataSource: RawDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : SavedDataApi {

    private var demoData: DemoData? = null

    override suspend fun getSavedDevices(): List<SavedDevice> = withContext(Dispatchers.IO) {
        (demoData ?: loadDemoData()).devices
    }

    override suspend fun getSavedUser(): SavedUser = withContext(Dispatchers.IO) {
        preferencesDataSource.getUser() ?: (demoData ?: loadDemoData()).user
    }

    override suspend fun saveUser(user: SavedUser) = withContext(Dispatchers.IO) {
        preferencesDataSource.saveUser(user)
    }

    private suspend fun loadDemoData(): DemoData {
        delay(ARTIFICIAL_DELAY) // artificial delay to show loader
        return rawDataSource.getDemoData()
    }

    companion object {
        private const val ARTIFICIAL_DELAY = 2000L
    }
}
