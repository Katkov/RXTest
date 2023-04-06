package com.example.rxtest.di

import com.example.rxtest.networking.NetworkingService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideNetworkingService(retrofit: Retrofit): NetworkingService =
        retrofit.create(NetworkingService::class.java)

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    private fun getGson() =
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
            .create()

    companion object {
        const val NETWORK_REQUEST_TIMEOUT_SECONDS = 15L
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }
}