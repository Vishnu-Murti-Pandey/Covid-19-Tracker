package com.example.covid_19tracker.vaccineSlot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19tracker.databinding.VaccineListItemBinding

class VaccineSlotAdapter(private val listener: VaccineItemClicked) :
    RecyclerView.Adapter<VaccineSlotAdapter.VaccineViewHolder>() {

    private val items: ArrayList<Vaccine> = ArrayList()

    class VaccineViewHolder(val binding: VaccineListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val view =
            VaccineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = VaccineViewHolder(view)
        view.root.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    fun updateNews(updatedNews: ArrayList<Vaccine>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.hospitalName.text = currentItem.hospital_name
        holder.binding.address.text = currentItem.address

        var addingTo = currentItem.from
        addingTo = "$addingTo to "
        holder.binding.from.text = addingTo

        holder.binding.to.text = currentItem.to

        var addingPlus = currentItem.min_age_limit
        addingPlus = "$addingPlus+"
        holder.binding.ageLimit.text = addingPlus

        holder.binding.brand.text = currentItem.vaccine_brand
        holder.binding.availability.text = currentItem.available_capacity
        holder.binding.feeType.text = currentItem.fee_type
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface VaccineItemClicked {
    fun onItemClicked(item: Vaccine)
}

