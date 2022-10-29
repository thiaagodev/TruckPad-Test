package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class LatLongGeoCodeDTO {

    @SerializedName("results")
    var result: List<LatLongResult> = listOf(LatLongResult())

}

class LatLongResult {

    @SerializedName("geometry")
    var geometry: Geometry = Geometry()
}

class Geometry {

    @SerializedName("location")
    var location: Location = Location()
}

class Location {

    @SerializedName("lat")
    var latitude: String = ""

    @SerializedName("lng")
    var longitude: String = ""
}