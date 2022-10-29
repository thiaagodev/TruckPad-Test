package com.thiaagodev.truckpad.service.constants

import com.thiaagodev.truckpad.BuildConfig

class TruckPadConstants private constructor() {
    object Retrofit {
        const val baseUrl = "https://api.homolog.truckpad.io/geo/v1/"
        const val IBGEBaseUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/"
        const val GeoCodeUrl = "https://maps.googleapis.com/maps/api/geocode/json"
    }

    object HTTP {
        const val SUCCESS = 200
    }

    object GOOGLE {
        const val KEY = BuildConfig.API_KEY
    }
}