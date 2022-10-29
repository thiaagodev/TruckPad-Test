package com.thiaagodev.truckpad.service.repository.remote

import com.thiaagodev.truckpad.service.dto.AddressGeoCodeDTO
import com.thiaagodev.truckpad.service.dto.LatLongGeoCodeDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GeoCodeService {

    @GET
    fun getLatLong(
        @Url url: String,
        @Query("address") address: String,
        @Query("key") key: String
    ): Call<LatLongGeoCodeDTO>

    @GET
    fun getAddress(
        @Url url: String,
        @Query("latlng") latlng: String,
        @Query("key") key: String
    ): Call<AddressGeoCodeDTO>

}