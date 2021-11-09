package dev.jx.composeweather

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.composeweather.data.remote.OpenWeatherService
import dev.jx.composeweather.data.remote.model.openweather.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val WEATHER_SERVICE = "Weather Service"

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val placesClient: PlacesClient
) : ViewModel() {

    val query = mutableStateOf("")
    val predictions: MutableState<List<AutocompletePrediction>> = mutableStateOf(listOf())
    val currentlyWeather: MutableState<CurrentWeather> = mutableStateOf(CurrentWeather.default)

    private var token: AutocompleteSessionToken? = null

    fun getPlacesPrediction(text: String) {
        if (text.isEmpty()) {
            predictions.value = listOf(); return
        }

        if (token == null) generateNewSessionToken()

        val request = FindAutocompletePredictionsRequest.builder().apply {
            sessionToken = token
            typeFilter = TypeFilter.CITIES
            query = text
        }.build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            predictions.value = response.autocompletePredictions
        }
    }

    private fun generateNewSessionToken() {
        token = AutocompleteSessionToken.newInstance()
    }

    private fun invalidateToken() {
        token = null
    }

    fun getWeatherInfo(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentlyWeather.value = try {
                val response = openWeatherService.getCurrentlyWeather(
                    cityName,
                    BuildConfig.OPEN_WEATHER_API_KEY
                )
                if (response.isSuccessful) {
                    response.body() ?: throw Throwable("Body is null")
                } else throw Throwable("Response is not successful")
            } catch (e: Throwable) {
                Log.e(WEATHER_SERVICE, e.message.toString())
                CurrentWeather.default
            }
        }

        // Weather Daily

        // Weather

        invalidateToken()
    }

}