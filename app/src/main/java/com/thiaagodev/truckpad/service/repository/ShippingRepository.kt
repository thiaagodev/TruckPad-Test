package com.thiaagodev.truckpad.service.repository

import android.content.Context
import com.google.gson.Gson
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.service.dto.ShippingDetailDTO
import com.thiaagodev.truckpad.service.dto.ShippingDetailRequestDTO
import com.thiaagodev.truckpad.service.dto.ShippingLoadPriceDTO
import com.thiaagodev.truckpad.service.dto.ShippingLoadPriceRequestDTO
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.service.repository.local.TruckPadDatabase
import com.thiaagodev.truckpad.service.repository.remote.RetrofitClient
import com.thiaagodev.truckpad.service.repository.remote.ShippingDetailService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShippingRepository(private val context: Context) {

    private val local = TruckPadDatabase.getDatabase(context).shippingDAO()
    private val remote = RetrofitClient.getService(ShippingDetailService::class.java)

    suspend fun save(shipping: ShippingModel): Long {
        return local.insert(shipping)
    }

    suspend fun list(): List<ShippingModel> {
        return local.list()
    }

    suspend fun get(id: Long): ShippingModel? {
        return local.get(id)
    }

    fun getDetails(shipping: ShippingDetailRequestDTO, listener: APIListener<ShippingDetailDTO>) {
        val call = remote.post(shipping)
        call.enqueue(object : Callback<ShippingDetailDTO> {
            override fun onResponse(
                call: Call<ShippingDetailDTO>,
                response: Response<ShippingDetailDTO>
            ) {
                if (response.code() == TruckPadConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    try {
                        listener.onFailure(failResponse(response.errorBody()!!.string()))
                    } catch (e: Exception) {
                        listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
                    }
                }
            }

            override fun onFailure(call: Call<ShippingDetailDTO>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    fun getLoadPrices(
        shippingLoadPriceRequestDTO: ShippingLoadPriceRequestDTO,
        listener: APIListener<ShippingLoadPriceDTO>
    ) {
        val call = remote.postLoadPrices(
            TruckPadConstants.Retrofit.loadPricesURL,
            shippingLoadPriceRequestDTO
        )
        call.enqueue(object : Callback<ShippingLoadPriceDTO> {
            override fun onResponse(
                call: Call<ShippingLoadPriceDTO>,
                response: Response<ShippingLoadPriceDTO>
            ) {
                if (response.code() == TruckPadConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    if (response.errorBody() != null) {
                        listener.onFailure(failResponse(response.errorBody()!!.string()))
                    } else {
                        listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
                    }
                }
            }

            override fun onFailure(call: Call<ShippingLoadPriceDTO>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    private fun failResponse(str: String): String = Gson().fromJson(str, String::class.java)

}