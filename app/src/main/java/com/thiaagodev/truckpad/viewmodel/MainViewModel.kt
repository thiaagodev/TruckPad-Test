package com.thiaagodev.truckpad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.service.repository.ShippingRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shippingRepository = ShippingRepository(application.applicationContext)

    private val _shippingList = MutableLiveData<List<ShippingModel>>()

    val shippingList: LiveData<List<ShippingModel>> = _shippingList

    fun listShipping() {
        viewModelScope.launch {
            _shippingList.value = shippingRepository.list()
        }
    }

}