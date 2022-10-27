package com.thiaagodev.truckpad.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thiaagodev.truckpad.R
import com.thiaagodev.truckpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.buttonCalcNewShipping.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_calc_new_shipping -> openShippingForm()
        }
    }

    private fun openShippingForm() {
        startActivity(
            Intent(this, ShippingCalcFormActivity::class.java)
        )

    }
}