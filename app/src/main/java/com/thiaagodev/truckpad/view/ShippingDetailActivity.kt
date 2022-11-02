package com.thiaagodev.truckpad.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityShippingDetailBinding
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.service.dto.ShippingDetailDTO
import com.thiaagodev.truckpad.service.dto.ShippingLoadPriceRequestDTO
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.view.adapter.ShippingLoadPriceAdapter
import com.thiaagodev.truckpad.viewmodel.ShippingDetailViewModel
import java.text.NumberFormat


class ShippingDetailActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    private lateinit var binding: ActivityShippingDetailBinding
    private lateinit var viewModel: ShippingDetailViewModel
    private lateinit var shipping: ShippingModel
    private val adapter = ShippingLoadPriceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShippingDetailViewModel::class.java]

        binding.imageClose.setOnClickListener(this)

        binding.recyclerPricePerLoad.layoutManager = LinearLayoutManager(this)
        binding.recyclerPricePerLoad.adapter = adapter

        observe()
        loadData()

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.image_close -> finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val origin = LatLng(shipping.originLatitude, shipping.originLongitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(origin)
                .title(shipping.originName)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 12.0f))
    }

    private fun observe() {
        viewModel.shipping.observe(this) {
            shipping = it
            viewModel.getDetails(it)
        }

        viewModel.shippingDetail.observe(this) {
            val shippingLoadPriceRequestDTO = ShippingLoadPriceRequestDTO()
            shippingLoadPriceRequestDTO.axis = shipping.axes
            shippingLoadPriceRequestDTO.distance = it.distance
            viewModel.getLoadPrices(shippingLoadPriceRequestDTO)

            bindData(it)
        }

        viewModel.shippingLoadPrices.observe(this) {
            adapter.updateShippingPrice(it.geListResults())
        }

        viewModel.validation.observe(this) {
            if (!it.status) {
                Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun loadData() {
        val bundle = intent.extras

        val shippingID = bundle?.getLong(TruckPadConstants.Shipping.ID)
        Log.d("ID", shippingID.toString())
        shippingID?.let {
            viewModel.getShipping(shippingID)
        }
    }

    private fun bindData(shippingDetail: ShippingDetailDTO) {

        val totalCost = NumberFormat.getCurrencyInstance().format(shippingDetail.totalCost)
        binding.textTotalCost.text = totalCost

        var distance = shippingDetail.distance
        if (shippingDetail.distance > 0) distance /= 1000

        binding.textDistance.text = "$distance KM"

        binding.textOriginCity.text = shipping.originName
        binding.textDestinyCity.text = shipping.destinyName

        var duration = shippingDetail.duration.toString()
        duration = if(shippingDetail.duration > 3600) "${shippingDetail.duration / 3600}h" else "${shippingDetail.duration} ${shippingDetail.durationUnit}"
        binding.textDuration.text = getString(R.string.duration, duration)

        binding.textTollCount.text = getString(R.string.toll_count, shippingDetail.tollCount.toString())

        val tollCost = NumberFormat.getCurrencyInstance().format(shippingDetail.tollCost)
        binding.textTollCost.text = getString(R.string.toll_cost, tollCost)

        binding.textDieselConsumption.text = getString(R.string.diesel_consumption, "${shippingDetail.fuelUsage.toString()}L")

        val fuelCost = NumberFormat.getCurrencyInstance().format(shippingDetail.fuelCost)
        binding.textDieselTotalPrice.text = getString(R.string.diesel_total_price, fuelCost)
    }

}