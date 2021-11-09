package dev.jx.composeweather.data.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jx.composeweather.BuildConfig
import dev.jx.composeweather.data.ApiConfig
import dev.jx.composeweather.data.remote.OpenWeatherService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideOpenWeatherService(): OpenWeatherService =
        ApiConfig.getApiService(OpenWeatherService::class.java, BuildConfig.OPEN_WEATHER_API_URL)

    @Singleton
    @Provides
    fun providePlacesClient(@ApplicationContext contextApp: Context): PlacesClient {
        Places.initialize(contextApp, BuildConfig.GOOGLE_PLACES_API_KEY)
        return Places.createClient(contextApp)
    }
}
