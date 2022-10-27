package com.thiaagodev.truckpad.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityShippingCalcFormBinding
import com.thiaagodev.truckpad.view.filter.MinMaxFilter
import com.thiaagodev.truckpad.viewmodel.ShippingCalcFormViewModel
import java.text.NumberFormat
import java.util.*


class ShippingCalcFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShippingCalcFormBinding
    private lateinit var viewModel: ShippingCalcFormViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingCalcFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShippingCalcFormViewModel::class.java]

        binding.fabClose.setOnClickListener(this)
        binding.buttonCalcShipping.setOnClickListener(this)
        binding.editAxes.filters = arrayOf(MinMaxFilter(2, 9))

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        observe()

        checkLocationPermission()
        viewModel.getCities()

        var current = ""

        binding.editFuelPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.toString() != current) {
                    binding.editFuelPrice.removeTextChangedListener(this)

                    var cleanString: String = s.toString()
                        .replace("R$", "")
                        .replace(",", "")
                        .replace(".", "")
                        .replace("\\s".toRegex(), "")


                    val parsed = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))

                    current = formatted
                    binding.editFuelPrice.setText(formatted)
                    binding.editFuelPrice.setSelection(formatted.length)

                    binding.editFuelPrice.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.editFuelConsumption.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.toString() != current) {
                    binding.editFuelConsumption.removeTextChangedListener(this)

                    var cleanString: String = s.toString()
                        .replace("L", "")
                        .replace(",", "")
                        .replace(".", "")
                        .replace("\\s".toRegex(), "")


                    val parsed = cleanString.toDouble()
                    var formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                    formatted = formatted.toString().replace("R$", "L")

                    current = formatted
                    binding.editFuelConsumption.setText(formatted)
                    binding.editFuelConsumption.setSelection(formatted.length)

                    binding.editFuelConsumption.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_close -> finish()
            R.id.button_calc_shipping -> calcShipping()
        }
    }

    private fun observe() {
        viewModel.cities.observe(this) {
            setEditAdapter(it)
        }

        viewModel.validation.observe(this) {
            if (!it.status) {
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setEditAdapter(list: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            list
        )

        binding.editOrigin.setAdapter(adapter)
        binding.editDestiny.setAdapter(adapter)
    }

    private fun calcShipping() {

    }

    private fun checkLocationPermission() {

        val task = fusedLocationClient.lastLocation

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

        task.addOnSuccessListener {

            try {
                val geoCoder = Geocoder(this, Locale.getDefault())
                val address = geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                binding.editOrigin.setText("${address[0].subAdminArea}, ${address[0].adminArea}")
            } catch (exception: Exception) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.locale_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


}