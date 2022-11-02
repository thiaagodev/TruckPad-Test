package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class ShippingLoadPriceRequestDTO {

    @SerializedName("axis")
    var axis: Int = 0

    @SerializedName("distance")
    var distance: Double = 0.0

    @SerializedName("has_return_shipment")
    var hasReturnShipment = true
}