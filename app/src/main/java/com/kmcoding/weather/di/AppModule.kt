package com.kmcoding.weather.di

import com.kmcoding.weather.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}
