package dev.jx.composeweather

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jx.composeweather.data.remote.GooglePlacesService
import dev.jx.composeweather.data.remote.OpenWeatherService
import dev.jx.composeweather.data.remote.model.googleplaces.PlaceAutocompletePrediction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PREDICTION_SERVICE = "Prediction Service"

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val openWeatherService: OpenWeatherService,
    private val googlePlacesService: GooglePlacesService
) : ViewModel() {

    val query = mutableStateOf("")
    val predictions: MutableState<List<PlaceAutocompletePrediction>> = mutableStateOf(listOf())

    fun getPlaces(text: String) {
        if (text.isEmpty()) {
            predictions.value = listOf(); return
        }
        viewModelScope.launch(Dispatchers.IO) {
            predictions.value = try {
                val response = googlePlacesService.getPlace(text, BuildConfig.GOOGLE_PLACES_API_KEY)
                if (response.isSuccessful) {
                    Log.d("TEST", "Result: ${response.body()?.predictions?.first()
                        ?.description}")
                    response.body()?.predictions ?: throw Throwable("Body is null")
                } else throw Throwable("Response is not successful")
            } catch (e: Throwable) {
                Log.e(PREDICTION_SERVICE, e.message.toString())
                Log.e(PREDICTION_SERVICE, e.stackTraceToString())
                listOf()
            }
        }
    }

}