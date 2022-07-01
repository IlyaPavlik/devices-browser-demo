package com.example.datastore.data

import android.content.Context
import com.example.datastore.R
import com.example.datastore.data.model.DemoData
import com.example.datastore.data.model.SavedDevice
import com.example.datastore.data.model.SavedUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DemoSavedDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : SavedDataApi {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    private val jsonMapper = jacksonObjectMapper()
    private var demoData: DemoData? = null

    override suspend fun getSavedDevices(): List<SavedDevice> = withContext(Dispatchers.IO) {
        (demoData ?: loadDemoData()).devices
    }

    override suspend fun getSavedUser(): SavedUser = withContext(Dispatchers.IO) {
        loadUserFromPreferences() ?: (demoData ?: loadDemoData()).user
    }

    override suspend fun saveUser(user: SavedUser) {
        val userJson = jsonMapper.writeValueAsString(user)
        with(sharedPreferences.edit()) {
            putString(KEY_USER, userJson)
            apply()
        }
    }

    private fun loadDemoData(): DemoData {
        return jsonMapper.readValue<DemoData>(context.resources.openRawResource(R.raw.data))
            .also { demoData = it }
    }

    private fun loadUserFromPreferences(): SavedUser? {
        return sharedPreferences.getString(KEY_USER, null)?.let { userJson ->
            jsonMapper.readValue(userJson)
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "DeviceBrowser"
        private const val KEY_USER = "key_user"
    }
}
