package com.example.datastore.data.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object JsonModule {

    @Provides
    fun provideJsonMapper(): ObjectMapper = jacksonObjectMapper()

}
