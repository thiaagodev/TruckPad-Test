package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class ShippingLoadPriceDTO {

    @SerializedName("frigorificada")
    var refrigerated: Double = 0.0

    @SerializedName("geral")
    var general: Double = 0.0

    @SerializedName("granel")
    var granel: Double = 0.0

    @SerializedName("neogranel")
    var neogranel: Double = 0.0

    @SerializedName("perigosa")
    var dangerous: Double = 0.0

    fun geListResults(): List<ShippingPrice> {
        return listOf(
            ShippingPrice("frigorificada", refrigerated),
            ShippingPrice("geral", general),
            ShippingPrice("granel", granel),
            ShippingPrice("neogranel", neogranel),
            ShippingPrice("perigosa", dangerous)
        )
    }
}

data class ShippingPrice(
    val name: String,
    val value: Double
)