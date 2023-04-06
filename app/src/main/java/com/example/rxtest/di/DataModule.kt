package com.example.rxtest.di

import com.example.rxtest.networking.NetworkingService
import com.example.rxtest.repository.RemoteRepository
import com.example.rxtest.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    fun providesRepository(networkingService: NetworkingService): Repository =
        RemoteRepository(networkingService)
}