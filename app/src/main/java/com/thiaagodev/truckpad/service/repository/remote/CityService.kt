package com.thiaagodev.truckpad.service.repository.remote

import com.thiaagodev.truckpad.service.dto.CityDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CityService {

    @GET()
    fun list(@Url url: String, @Query("view") query: String): Call<List<CityDTO>>
}