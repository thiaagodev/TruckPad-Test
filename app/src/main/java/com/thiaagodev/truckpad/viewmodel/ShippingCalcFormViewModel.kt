package com.thiaagodev.truckpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thiaagodev.truckpad.service.dto.CityDTO
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.model.ValidationModel
import com.thiaagodev.truckpad.service.repository.CityRepository

class ShippingCalcFormViewModel(application: Application) : AndroidViewModel(application) {

    private val cityRepository = CityRepository(application.applicationContext)

    private val _validation = MutableLiveData<ValidationModel>()
    private val _cities = MutableLiveData<List<String>>()

    val validation: LiveData<ValidationModel> = _validation
    val cities: LiveData<List<String>> = _cities

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

}