package com.example.datastore.data.module

import com.example.datastore.data.DemoSavedDataRepository
import com.example.datastore.data.SavedDataApi
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
        demoDataRepository: DemoSavedDataRepository
    ): SavedDataApi
}
