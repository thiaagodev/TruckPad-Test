package com.thiaagodev.truckpad.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityShippingCalcFormBinding
import com.thiaagodev.truckpad.service.constants.TruckPadConstants
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.view.filter.MinMaxFilter
import com.thiaagodev.truckpad.viewmodel.ShippingCalcFormViewModel
import java.text.NumberFormat
import java.util.*


class ShippingCalcFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShippingCalcFormBinding
    private lateinit var viewModel: ShippingCalcFormViewModel
    private lateinit var cities: List<String>
    private val currency = Currency.getInstance(Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingCalcFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShippingCalcFormViewModel::class.java]

        binding.fabClose.setOnClickListener(this)
        binding.buttonCalcShipping.setOnClickListener(this)
        binding.editAxes.filters = arrayOf(MinMaxFilter(2, 9))

        observe()

        viewModel.checkLocationPermission(this)
        viewModel.getCities()

        binding.editOrigin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && ::cities.isInitialized) {
                if (!cities.contains(binding.editOrigin.text.toString())) {
                    binding.editOrigin.setText("")
                    wrongCityAlert()
                }
            }
        }

        binding.editDestiny.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && ::cities.isInitialized) {
                if (!cities.contains(binding.editDestiny.text.toString())) {
                    binding.editDestiny.setText("")
                    wrongCityAlert()
                }
            }
        }

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

                    val cleanString: String = s.toString()
                        .replace(currency.symbol, "")
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

            override fun afterTextChanged(p0: Editable?) {}

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

                    val cleanString: String = s.toString()
                        .replace("Km/L", "")
                        .replace(",", "")
                        .replace(".", "")
                        .replace("\\s".toRegex(), "")


                    val parsed = cleanString.toDouble()
                    var formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                    formatted = formatted.toString().replace(currency.symbol, "Km/L ")

                    current = formatted
                    binding.editFuelConsumption.setText(formatted)
                    binding.editFuelConsumption.setSelection(formatted.length)

                    binding.editFuelConsumption.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_close -> finish()
            R.id.button_calc_shipping -> saveShipping()
        }
    }

    private fun observe() {
        viewModel.cities.observe(this) {
            cities = it
            setEditAdapter(it)
        }

        viewModel.validation.observe(this) {
            if (!it.status) {
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.cityName.observe(this) {
            binding.editOrigin.setText(it)
        }

        viewModel.createdShippingID.observe(this) {
            val intent = Intent(applicationContext, ShippingDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putLong(TruckPadConstants.Shipping.ID, it)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
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

    private fun wrongCityAlert() {
        val snackBarView = Snackbar.make(
            binding.root,
            getString(R.string.wrong_city_name),
            Snackbar.LENGTH_LONG
        )
        val view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    private fun saveShipping() {

        try {
            val originName = binding.editOrigin.text.toString()
            val destinyName = binding.editDestiny.text.toString()
            val axes = binding.editAxes.text.toString().toInt()
            val fuelConsumption = binding.editFuelConsumption.text.toString()
                .replace("Km/L", "")
                .replace(",", ".")
                .replace("\\s".toRegex(), "")
                .toDouble()
            val fuelPrice = binding.editFuelPrice.text.toString()
                .replace(currency.symbol, "")
                .replace(",", ".")
                .replace("\\s".toRegex(), "")
                .toDouble()

            val shipping = ShippingModel()
            shipping.originName = originName
            shipping.destinyName = destinyName
            shipping.axes = axes
            shipping.fuelConsumption = fuelConsumption
            shipping.fuelPrice = fuelPrice

            viewModel.saveShippingWithLatLong(shipping)
        } catch (e: Exception) {
            Snackbar.make(
                binding.root,
                getString(R.string.ERROR_SHIPPING_VALIDATION),
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

}