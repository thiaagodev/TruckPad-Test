package com.thiaagodev.truckpad.service.repository

import android.content.Context
import com.google.gson.Gson
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.service.dto.CityDTO
import com.thiaagodev.truckpad.service.listener.APIListener
import com.thiaagodev.truckpad.service.repository.remote.CityService
import com.thiaagodev.truckpad.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityRepository(private val context: Context) {

    private val remote = RetrofitClient.getService(CityService::class.java)

    fun list(listener: APIListener<List<CityDTO>>) {
        val call = remote.list(TruckPadConstants.Retrofit.IBGEBaseUrl, "nivelado")
        call.enqueue(object : Callback<List<CityDTO>> {
            override fun onResponse(call: Call<List<CityDTO>>, response: Response<List<CityDTO>>) {
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

            override fun onFailure(call: Call<List<CityDTO>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }

        })
    }

    private fun failResponse(str: String): String = Gson().fromJson(str, String::class.java)

}