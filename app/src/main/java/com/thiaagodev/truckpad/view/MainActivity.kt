package com.thiaagodev.truckpad.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityMainBinding
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.view.adapter.ShippingAdapter
import com.thiaagodev.truckpad.view.listener.OnShippingListener
import com.thiaagodev.truckpad.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = ShippingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.recyclerShipping.layoutManager = LinearLayoutManager(this)
        binding.recyclerShipping.adapter = adapter

        adapter.attachListener(object : OnShippingListener {
            override fun onClick(id: Long) {
                val intent = Intent(applicationContext, ShippingDetailActivity::class.java)
                val bundle = Bundle()
                Log.d("MAINID", id.toString())
                bundle.putLong(TruckPadConstants.Shipping.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)

            }

        })

        binding.buttonCalcNewShipping.setOnClickListener(this)

        getLocationPermission()

        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.listShipping()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_calc_new_shipping -> openShippingForm()
        }
    }

    private fun observe() {
        viewModel.shippingList.observe(this) {
            if (it.isNotEmpty()) binding.textNoShipping.visibility = View.INVISIBLE
            adapter.updateShipping(it)
        }
    }

    private fun openShippingForm() {
        startActivity(
            Intent(this, ShippingCalcFormActivity::class.java)
        )

    }

    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
    }
}