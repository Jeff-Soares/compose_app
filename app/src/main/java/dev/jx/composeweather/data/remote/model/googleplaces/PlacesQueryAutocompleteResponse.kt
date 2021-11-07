package dev.jx.composeweather.data.remote.model.googleplaces

import com.google.gson.annotations.SerializedName

data class PlacesQueryAutocompleteResponse(
    @SerializedName("predictions")
    val predictions: List<PlaceAutocompletePrediction>,
    @SerializedName("status")
    val status: AutocompleteStatus,
    @SerializedName("error_message")
    val errorMessage: String? = null,
    @SerializedName("info_messages")
    val infoMessages: List<String?>? = null
)