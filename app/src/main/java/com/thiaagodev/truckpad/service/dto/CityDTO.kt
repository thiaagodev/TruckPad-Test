package com.thiaagodev.truckpad.service.dto

import com.google.gson.annotations.SerializedName

class CityDTO {

    @SerializedName("municipio-id")
    var id: Long = 0

    @SerializedName("municipio-nome")
    var name = ""

    @SerializedName("UF-nome")
    var uf = ""


    fun getCityStateName(): String {
        return "$name, $uf"
    }
}