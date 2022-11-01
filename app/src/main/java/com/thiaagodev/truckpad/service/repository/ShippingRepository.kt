package com.thiaagodev.truckpad.service.repository

import android.content.Context
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.service.repository.local.TruckPadDatabase

class ShippingRepository(private val context: Context) {

    private val local = TruckPadDatabase.getDatabase(context).shippingDAO()

    suspend fun save(shipping: ShippingModel): Long {
        return local.insert(shipping)
    }

    suspend fun list(): List<ShippingModel> {
        return local.list()
    }

    suspend fun get(id: Int): ShippingModel? {
        return local.get(id)
    }

}