package com.example.datastore.data.source

import android.content.Context
import androidx.annotation.RawRes
import com.example.datastore.R
import com.example.datastore.data.model.DemoData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RawDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val jsonMapper: ObjectMapper
) {

    fun getDemoData(): DemoData = getData(R.raw.data)

    private inline fun <reified T> getData(@RawRes rawIdRes: Int): T {
        return jsonMapper.readValue<T>(context.resources.openRawResource(rawIdRes))
    }

}
