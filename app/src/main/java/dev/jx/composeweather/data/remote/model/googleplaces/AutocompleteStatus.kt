package dev.jx.composeweather.data.remote.model.googleplaces

import com.google.gson.annotations.SerializedName

enum class AutocompleteStatus {

    @SerializedName("OK")
    OK,

    @SerializedName("ZERO_RESULTS")
    ZERO_RESULTS,

    @SerializedName("INVALID_REQUEST")
    INVALID_REQUEST,

    @SerializedName("OVER_QUERY_LIMIT")
    OVER_QUERY_LIMIT,

    @SerializedName("REQUEST_DENIED")
    REQUEST_DENIED,

    @SerializedName("UNKNOWN_ERROR")
    UNKNOWN_ERROR

}