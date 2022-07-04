package com.example.network.data.source

import com.example.network.data.model.RemoteData
import javax.inject.Inject

internal class DataCacheSource @Inject constructor() {

    private var remoteData: RemoteData? = null

    fun getData(): RemoteData? = remoteData

    fun saveData(remoteData: RemoteData) {
        this.remoteData = remoteData
    }
}
