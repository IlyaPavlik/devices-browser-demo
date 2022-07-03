package com.example.datastore.data.source

import android.content.Context
import com.example.datastore.data.model.SavedUser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val jsonMapper: ObjectMapper
) {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun getUser(): SavedUser? = sharedPreferences.getString(KEY_USER, null)
        ?.let { userJson -> jsonMapper.readValue(userJson) }

    fun saveUser(user: SavedUser) {
        val userJson = jsonMapper.writeValueAsString(user)
        with(sharedPreferences.edit()) {
            putString(KEY_USER, userJson)
            apply()
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "DeviceBrowser"
        private const val KEY_USER = "key_user"
    }
}
