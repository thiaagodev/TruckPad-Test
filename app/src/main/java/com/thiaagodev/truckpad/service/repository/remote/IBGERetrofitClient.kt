package com.thiaagodev.truckpad.service.repository.remote

import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class IBGERetrofitClient private constructor() {

    companion object {
        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitClient(): Retrofit {
            if (!::INSTANCE.isInitialized) {

                val httpClient = OkHttpClient.Builder()

                synchronized(IBGERetrofitClient::class) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl(TruckPadConstants.Retrofit.IBGEBaseUrl)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }

            return INSTANCE
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return getRetrofitClient().create(serviceClass)
        }

    }

}