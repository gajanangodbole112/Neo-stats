package com.gajanan.asteroid_neostats.network

import com.gajanan.asteroid_neostats.models.NearEarthObjects
import com.gajanan.asteroid_neostats.models.NeoStats
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("feed")
    fun getNeoStats(
        @Query("api_key") apiKey:String,
        @Query("start_date") startDate : String,
        @Query("end_date") endDate: String
    ) : Call<NeoStats>
}