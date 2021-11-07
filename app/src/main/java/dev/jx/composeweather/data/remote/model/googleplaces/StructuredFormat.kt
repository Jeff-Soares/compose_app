package dev.jx.composeweather.data.remote.model.googleplaces


import com.google.gson.annotations.SerializedName

data class StructuredFormat(
    @SerializedName("main_text")
    val mainText: String,
    @SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MatchedSubstring>,
    @SerializedName("secondary_text")
    val secondaryText: String,
    @SerializedName("secondary_text_matched_substrings")
    val secondaryTextMatchedSubstrings: List<MatchedSubstring?>? = null
)