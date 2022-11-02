package com.thiaagodev.truckpad.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.thiaagodev.truckpad.databinding.ShippingLoadPriceItemBinding
import com.thiaagodev.truckpad.service.dto.ShippingPrice
import com.thiaagodev.truckpad.view.listener.OnShippingListener
import java.text.NumberFormat

class ShippingLoadPriceViewHolder(private val binding: ShippingLoadPriceItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(shippingPrice: ShippingPrice) {
        binding.textLoadPrice.text = "${shippingPrice.name}: ${
            NumberFormat.getCurrencyInstance().format(shippingPrice.value)
        }"
    }

}