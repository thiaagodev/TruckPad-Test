package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class ShippingDetailDTO {

    @SerializedName("distance")
    var distance: Double = 0.0

    @SerializedName("distance_unit")
    var distanceUnit: String = ""

    @SerializedName("duration")
    var duration: Double = 0.0

    @SerializedName("duration_unit")
    var durationUnit: String = ""

    @SerializedName("toll_count")
    var tollCount: Int = 0

    @SerializedName("toll_cost")
    var tollCost: Double = 0.0

    @SerializedName("toll_cost_unit")
    var tollCostUnit: String = ""

    @SerializedName("route")
    var route: List<List<List<Double>>> = listOf()

    @SerializedName("fuel_usage")
    var fuelUsage: Double = 0.0

    @SerializedName("fuel_usage_unit")
    var fuelUsageUnit: String = ""

    @SerializedName("fuel_cost")
    var fuelCost: Double = 0.0

    @SerializedName("fuel_cost_unit")
    var fuelCostUnit: String = ""

    @SerializedName("total_cost")
    var totalCost: Double = 0.0

}