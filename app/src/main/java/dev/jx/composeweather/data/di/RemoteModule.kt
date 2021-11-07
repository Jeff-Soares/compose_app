package dev.jx.composeweather.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jx.composeweather.BuildConfig
import dev.jx.composeweather.data.ApiConfig
import dev.jx.composeweather.data.remote.GooglePlacesService
import dev.jx.composeweather.data.remote.OpenWeatherService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideGooglePlacesService(): GooglePlacesService =
        ApiConfig.getApiService(GooglePlacesService::class.java, BuildConfig.GOOGLE_PLACES_API_URL)

}