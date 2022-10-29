package com.thiaagodev.truckpad.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thiaagodev.truckpad.service.dto.AddressGeoCodeDTO
import com.thiaagodev.truckpad.service.dto.CityDTO
import com.thiaagodev.truckpad.service.dto.LatLongGeoCodeDTO
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.service.model.ValidationModel
import com.thiaagodev.truckpad.service.repository.CityRepository
import com.thiaagodev.truckpad.service.repository.GeoCodeRepository
import com.thiaagodev.truckpad.service.repository.ShippingRepository
import kotlinx.coroutines.launch

class ShippingCalcFormViewModel(application: Application) : AndroidViewModel(application) {

    private val cityRepository = CityRepository(application.applicationContext)
    private val shippingRepository = ShippingRepository(application.applicationContext)
    private val geoCodeRepository = GeoCodeRepository(application.applicationContext)

    private val ai: ApplicationInfo = application.applicationContext.packageManager
        .getApplicationInfo(
            application.applicationContext.packageName,
            PackageManager.GET_META_DATA
        )

    private val API_KEY: String = (ai.metaData["API_KEY"] ?: "") as String

    private val _validation = MutableLiveData<ValidationModel>()
    private val _cities = MutableLiveData<List<String>>()
    private val _cityName = MutableLiveData<String>()

    val validation: LiveData<ValidationModel> = _validation
    val cities: LiveData<List<String>> = _cities
    val cityName: LiveData<String> = _cityName


    fun getCities() {
        cityRepository.list(object : APIListener<List<CityDTO>> {
            override fun onSuccess(result: List<CityDTO>) {
                _cities.value = result.map { it.getCityStateName() }
            }

            override fun onFailure(message: String) {
                _validation.value = ValidationModel(false, message)
            }

        })
    }

    fun getCityName(latlng: String) {
        geoCodeRepository.getAddress(latlng, API_KEY, object : APIListener<AddressGeoCodeDTO> {
            override fun onSuccess(result: AddressGeoCodeDTO) {
                val cityName =
                    result.result[0].addressResult.filter { it.types.contains("administrative_area_level_2") }

                val stateName =
                    result.result[0].addressResult.filter { it.types.contains("administrative_area_level_1") }

                _cityName.value = "${cityName[0].name}, ${stateName[0].name}"
            }

            override fun onFailure(message: String) {
                _validation.value = ValidationModel(false, message)
            }

        })
    }

    fun saveShippingWithLatLong(shipping: ShippingModel) {
        geoCodeRepository.getLatLong(
            shipping.originName,
            API_KEY,
            object : APIListener<LatLongGeoCodeDTO> {
                override fun onSuccess(result: LatLongGeoCodeDTO) {
                    shipping.originLatitude = result.result[0].geometry.location.latitude.toDouble()
                    shipping.originLongitude =
                        result.result[0].geometry.location.longitude.toDouble()

                    geoCodeRepository.getLatLong(
                        shipping.destinyName,
                        API_KEY,
                        object : APIListener<LatLongGeoCodeDTO> {
                            override fun onSuccess(result: LatLongGeoCodeDTO) {
                                shipping.destinyLatitude =
                                    result.result[0].geometry.location.latitude.toDouble()
                                shipping.destinyLongitude =
                                    result.result[0].geometry.location.longitude.toDouble()

                                saveShipping(shipping)

                            }

                            override fun onFailure(message: String) {
                                _validation.value = ValidationModel(false, message)
                            }

                        })

                }

                override fun onFailure(message: String) {
                    _validation.value = ValidationModel(false, message)
                }

            })
    }

    private fun saveShipping(shipping: ShippingModel) {
        viewModelScope.launch {
            shippingRepository.save(shipping)
        }
    }

}