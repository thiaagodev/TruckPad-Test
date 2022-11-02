package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class ShippingDetailRequestDTO {

    @SerializedName("places")
    var places: List<Point> = listOf()

    @SerializedName("fuel_consumption")
    var fuelConsumption: Double = 0.0

    @SerializedName("fuel_price")
    var fuelPrice: Double = 0.0

}

class Point {

    @SerializedName("point")
    var point: List<Double> = listOf()
}