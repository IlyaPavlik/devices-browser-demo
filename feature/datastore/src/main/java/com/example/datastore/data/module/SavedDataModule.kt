package com.example.datastore.data.module

import com.example.datastore.data.SavedDataApi
import com.example.datastore.data.SavedDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SavedDataModule {

    @Binds
    @Singleton
    abstract fun bindSavedDataApi(
        demoDataRepository: SavedDataRepository
    ): SavedDataApi
}
