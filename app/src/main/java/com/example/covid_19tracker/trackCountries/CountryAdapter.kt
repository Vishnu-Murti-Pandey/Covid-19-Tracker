package com.example.covid_19tracker.trackCountries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covid_19tracker.databinding.CountryListBinding
import com.example.covid_19tracker.retrofitApi.CountryData
import java.text.NumberFormat

class CountryAdapter(private val listener: SelectCountry) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var items: ArrayList<CountryData> = ArrayList()

    class CountryViewHolder(val binding: CountryListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = CountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = CountryViewHolder(view)
        view.root.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    fun filterCountry(countryList: ArrayList<CountryData>) {
        items = countryList
        notifyDataSetChanged()
    }

    fun updateCountry(updatedCountry: ArrayList<CountryData>) {
        items.clear()
        items.addAll(updatedCountry)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.totalCases.text =
            NumberFormat.getInstance().format(Integer.parseInt(currentItem.cases))
        holder.binding.countryName.text = currentItem.country
        holder.binding.id.text = (position + 1).toString()

        val flagImage: Map<String, String> = currentItem.countryInfo

        Glide.with(holder.itemView.context).load(flagImage["flag"]).into(holder.binding.flag)


    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface CountryItemClicked {
    fun onItemClicked(item: CountryData)
}