package dev.jx.composeweather.data.remote.model.googleplaces


import com.google.gson.annotations.SerializedName

data class PlaceAutocompletePrediction(
    @SerializedName("description")
    val description: String,
    @SerializedName("matched_substrings")
    val matchedSubstrings: List<MatchedSubstring>,
    @SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormat,
    @SerializedName("terms")
    val terms: List<Term>,
    @SerializedName("place_id")
    val placeId: String? = null,
    @SerializedName("reference")
    val reference: String? = null,
    @SerializedName("types")
    val types: List<String?>? = null
)