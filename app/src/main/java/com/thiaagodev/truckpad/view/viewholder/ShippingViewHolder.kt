package com.thiaagodev.truckpad.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.thiaagodev.truckpad.databinding.ShippingHistoryItemBinding
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.view.listener.OnShippingListener

class ShippingViewHolder(
    private val binding: ShippingHistoryItemBinding,
    private val listener: OnShippingListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(shipping: ShippingModel) {
        binding.textOrigin.text = shipping.originName
        binding.textDestiny.text = shipping.destinyName

        binding.root.setOnClickListener {
            listener.onClick(shipping.id.toLong())
        }

    }

}