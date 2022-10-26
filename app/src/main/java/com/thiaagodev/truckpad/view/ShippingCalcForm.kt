package com.thiaagodev.truckpad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityShippingCalcFormBinding
import com.thiaagodev.truckpad.view.filter.MinMaxFilter

class ShippingCalcForm : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShippingCalcFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingCalcFormBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fabClose.setOnClickListener(this)
        binding.editAxes.filters = arrayOf(MinMaxFilter(2, 9))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_close -> finish()
        }
    }
}