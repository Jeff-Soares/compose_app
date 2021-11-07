package dev.jx.composeweather.data.remote

import dev.jx.composeweather.data.remote.model.googleplaces.PlacesQueryAutocompleteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesService {

    @GET("place/queryautocomplete/json")
    suspend fun getPlace(
        @Query("input") text: String,
        @Query("key") key: String
    ): Response<PlacesQueryAutocompleteResponse>

}