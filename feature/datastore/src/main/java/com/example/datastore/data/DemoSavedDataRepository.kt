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

    private var demoData: DemoData? = null

    override suspend fun getSavedDevices(): List<SavedDevice> = withContext(Dispatchers.IO) {
        (demoData ?: loadDemoData()).devices
    }

    override suspend fun getSavedUser(): SavedUser = withContext(Dispatchers.IO) {
        (demoData ?: loadDemoData()).user
    }

    private fun loadDemoData(): DemoData {
        val mapper = jacksonObjectMapper()
        return mapper.readValue<DemoData>(context.resources.openRawResource(R.raw.data))
            .also { demoData = it }
    }
}
