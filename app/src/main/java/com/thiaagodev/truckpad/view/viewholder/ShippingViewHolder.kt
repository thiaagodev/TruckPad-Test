package com.thiaagodev.truckpad.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.thiaagodev.truckpad.databinding.ShippingHistoryItemBinding
import com.thiaagodev.truckpad.service.model.ShippingModel

class ShippingViewHolder(private val binding: ShippingHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(shipping: ShippingModel) {
        binding.textOrigin.text = shipping.originName
        binding.textDestiny.text = shipping.destinyName
    }

}