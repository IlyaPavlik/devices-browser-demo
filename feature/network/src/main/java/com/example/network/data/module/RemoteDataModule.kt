package com.example.network.data.module

import com.example.network.data.DataRepository
import com.example.network.data.RemoteDataApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataApi(dataRepository: DataRepository): RemoteDataApi
}
