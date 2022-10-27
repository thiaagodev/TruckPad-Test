package com.thiaagodev.truckpad.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityShippingCalcFormBinding
import com.thiaagodev.truckpad.view.filter.MinMaxFilter
import com.thiaagodev.truckpad.viewmodel.ShippingCalcFormViewModel


class ShippingCalcFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShippingCalcFormBinding
    private lateinit var viewModel: ShippingCalcFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingCalcFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShippingCalcFormViewModel::class.java]

        binding.fabClose.setOnClickListener(this)
        binding.editAxes.filters = arrayOf(MinMaxFilter(2, 9))

        observe()

        viewModel.getCities()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_close -> finish()
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

}