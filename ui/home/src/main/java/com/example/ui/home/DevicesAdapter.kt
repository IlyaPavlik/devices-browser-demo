package com.example.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature.device.data.model.Device
import com.example.ui.home.databinding.ItemDeviceBinding

class DevicesAdapter : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    var items: List<Device> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DeviceViewHolder(parent)

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class DeviceViewHolder(
        parent: ViewGroup,
        private val itemBinding: ItemDeviceBinding = ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(device: Device) {
            itemBinding.name.text = device.deviceName
        }
    }
}
