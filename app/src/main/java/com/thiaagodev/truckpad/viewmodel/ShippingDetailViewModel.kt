package com.thiaagodev.truckpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thiaagodev.truckpad.service.dto.*
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.service.model.ValidationModel
import com.thiaagodev.truckpad.service.repository.ShippingRepository
import kotlinx.coroutines.launch

class ShippingDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val shippingRepository = ShippingRepository(application.applicationContext)

    private val _validation = MutableLiveData<ValidationModel>()
    private val _shipping = MutableLiveData<ShippingModel>()
    private val _shippingDetail = MutableLiveData<ShippingDetailDTO>()
    private val _shippingLoadPrices = MutableLiveData<ShippingLoadPriceDTO>()

    val validation: LiveData<ValidationModel> = _validation
    val shipping: LiveData<ShippingModel> = _shipping
    val shippingDetail: LiveData<ShippingDetailDTO> = _shippingDetail
    val shippingLoadPrices: LiveData<ShippingLoadPriceDTO> = _shippingLoadPrices

    fun getShipping(id: Long) {
        viewModelScope.launch {
            _shipping.value = shippingRepository.get(id)
        }
    }

    fun getDetails(shipping: ShippingModel) {

        viewModelScope.launch {

            val shippingDetailRequest = ShippingDetailRequestDTO()

            val pointOrigin = Point()
            pointOrigin.point = listOf(shipping.originLatitude, shipping.originLongitude)

            val pointDestiny = Point()
            pointDestiny.point = listOf(shipping.destinyLatitude, shipping.destinyLongitude)

            shippingDetailRequest.places = listOf(pointOrigin, pointDestiny)
            shippingDetailRequest.fuelConsumption = shipping.fuelConsumption
            shippingDetailRequest.fuelPrice = shipping.fuelPrice

            shippingRepository.getDetails(
                shippingDetailRequest,
                object : APIListener<ShippingDetailDTO> {
                    override fun onSuccess(result: ShippingDetailDTO) {
                        _shippingDetail.value = result
                    }

                    override fun onFailure(message: String) {
                        _validation.value = ValidationModel(false, message)
                    }

                })
        }
    }

    fun getLoadPrices(shippingLoadPriceRequestDTO: ShippingLoadPriceRequestDTO) {
        viewModelScope.launch {
            shippingRepository.getLoadPrices(
                shippingLoadPriceRequestDTO,
                object : APIListener<ShippingLoadPriceDTO> {
                    override fun onSuccess(result: ShippingLoadPriceDTO) {
                        _shippingLoadPrices.value = result
                    }

                    override fun onFailure(message: String) {
                        _validation.value = ValidationModel(false, message)
                    }

                })
        }
    }

}