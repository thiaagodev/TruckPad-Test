package com.thiaagodev.truckpad.service.repository.remote

import com.thiaagodev.truckpad.service.dto.ShippingDetailDTO
import com.thiaagodev.truckpad.service.dto.ShippingDetailRequestDTO
import com.thiaagodev.truckpad.service.dto.ShippingLoadPriceDTO
import com.thiaagodev.truckpad.service.dto.ShippingLoadPriceRequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ShippingDetailService {

    @POST("route")
    fun post(@Body shipping: ShippingDetailRequestDTO): Call<ShippingDetailDTO>

    @POST
    fun postLoadPrices(
        @Url url: String,
        @Body shippingLoadPriceRequestDTO: ShippingLoadPriceRequestDTO
    ): Call<ShippingLoadPriceDTO>
}