package com.thiaagodev.truckpad.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thiaagodev.truckpad.databinding.ShippingHistoryItemBinding
import com.thiaagodev.truckpad.service.model.ShippingModel
import com.thiaagodev.truckpad.view.listener.OnShippingListener
import com.thiaagodev.truckpad.view.viewholder.ShippingViewHolder

class ShippingAdapter : RecyclerView.Adapter<ShippingViewHolder>() {

    private var shippingList: List<ShippingModel> = listOf()
    private lateinit var shippingListener: OnShippingListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShippingViewHolder {
        val item = ShippingHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ShippingViewHolder(item, shippingListener)
    }

    override fun onBindViewHolder(holder: ShippingViewHolder, position: Int) {
        holder.bind(shippingList[position])
    }

    override fun getItemCount(): Int {
        return shippingList.count()
    }

    fun updateShipping(list: List<ShippingModel>) {
        shippingList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: OnShippingListener) {
        shippingListener = listener
    }

}