package com.example.network.data.source

import com.example.network.data.model.RemoteData
import com.example.network.data.service.DataService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DataRemoteSource @Inject constructor(
    private val dataService: DataService
) {

    suspend fun getData(): RemoteData = dataService.getData().body()
        ?: throw IllegalStateException("No data found")
}
