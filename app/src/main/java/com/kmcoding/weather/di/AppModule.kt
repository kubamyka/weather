package com.kmcoding.weather.di

import android.content.Context
import androidx.room.Room
import com.kmcoding.weather.BuildConfig
import com.kmcoding.weather.data.db.AppDatabase
import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.data.remote.WeatherApi
import com.kmcoding.weather.data.repository.WeatherRepositoryImpl
import com.kmcoding.weather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
            .baseUrl("http://api.weatherapi.com/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    @Named("weatherApiKey")
    fun provideWeatherApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @Named("weatherLang")
    fun provideWeatherLang(): String = "pl"

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext
        context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao = appDatabase.locationDao()

    @Provides
    @Singleton
    fun provideWeatherRepository(
        locationDao: LocationDao,
        api: WeatherApi,
        @Named("weatherApiKey")
        apiKey: String,
        @Named("weatherLang")
        lang: String,
    ): WeatherRepository =
        WeatherRepositoryImpl(
            locationDao = locationDao,
            api = api,
            weatherApiKey = apiKey,
            weatherLang = lang,
        )
}
