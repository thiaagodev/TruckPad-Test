package com.thiaagodev.truckpad.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thiaagodev.truckpad.databinding.ShippingLoadPriceItemBinding
import com.thiaagodev.truckpad.service.dto.ShippingPrice
import com.thiaagodev.truckpad.view.viewholder.ShippingLoadPriceViewHolder

class ShippingLoadPriceAdapter : RecyclerView.Adapter<ShippingLoadPriceViewHolder>() {

    private var shippingPrices: List<ShippingPrice> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShippingLoadPriceViewHolder {
        val item = ShippingLoadPriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ShippingLoadPriceViewHolder(item)
    }

    override fun onBindViewHolder(holder: ShippingLoadPriceViewHolder, position: Int) {
        holder.bind(shippingPrices[position])
    }

    override fun getItemCount(): Int {
        return shippingPrices.count()
    }

    fun updateShippingPrice(list: List<ShippingPrice>) {
        shippingPrices = list
        notifyDataSetChanged()
    }

}