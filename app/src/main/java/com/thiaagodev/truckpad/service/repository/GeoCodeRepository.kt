package com.thiaagodev.truckpad.service.repository

import android.content.Context
import com.google.gson.Gson
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.service.dto.AddressGeoCodeDTO
import com.thiaagodev.truckpad.service.dto.CityDTO
import com.thiaagodev.truckpad.service.dto.LatLongGeoCodeDTO
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.repository.remote.GeoCodeService
import com.thiaagodev.truckpad.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeoCodeRepository(private val context: Context) {

    private val remote = RetrofitClient.getService(GeoCodeService::class.java)

    fun getLatLong(address: String,  API_KEY: String, listener: APIListener<LatLongGeoCodeDTO>) {
        val call = remote.getLatLong(
            TruckPadConstants.Retrofit.GeoCodeUrl,
            address,
            API_KEY
        )
        call.enqueue(object : Callback<LatLongGeoCodeDTO> {
            override fun onResponse(
                call: Call<LatLongGeoCodeDTO>,
                response: Response<LatLongGeoCodeDTO>
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

            override fun onFailure(call: Call<LatLongGeoCodeDTO>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    fun getAddress(latlng: String, API_KEY: String,listener: APIListener<AddressGeoCodeDTO>) {
        val call = remote.getAddress(
            TruckPadConstants.Retrofit.GeoCodeUrl,
            latlng,
            API_KEY
        )
        call.enqueue(object : Callback<AddressGeoCodeDTO> {
            override fun onResponse(
                call: Call<AddressGeoCodeDTO>,
                response: Response<AddressGeoCodeDTO>
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

            override fun onFailure(call: Call<AddressGeoCodeDTO>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    private fun failResponse(str: String): String = Gson().fromJson(str, String::class.java)

}