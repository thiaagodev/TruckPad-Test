package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class AddressGeoCodeDTO {

    @SerializedName("results")
    var result: List<AddressResult> = listOf(AddressResult())

}

class AddressResult {

    @SerializedName("address_components")
    var addressResult: List<AddressComponents> = listOf(AddressComponents())
}

class AddressComponents {

    @SerializedName("long_name")
    var name: String = ""

    @SerializedName("types")
    var types: List<String> = listOf("")
}