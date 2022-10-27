package com.thiaagodev.truckpad.service.repository.remote

import com.thiaagodev.truckpad.service.dto.CityDTO
import retrofit2.Call
import retrofit2.http.GET

interface CityService {

    @GET("municipios?view=nivelado")
    fun list(): Call<List<CityDTO>>
}