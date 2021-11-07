package dev.jx.composeweather.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {

    companion object {
        fun <T> getApiService(service: Class<T>, url: String): T = Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)

        private fun getClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

}