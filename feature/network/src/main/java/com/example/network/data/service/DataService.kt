package com.example.network.data.service

import com.example.network.data.model.RemoteData
import retrofit2.Response
import retrofit2.http.GET

internal interface DataService {

    @GET("modulotest/data.json")
    suspend fun getData(): Response<RemoteData>

}
