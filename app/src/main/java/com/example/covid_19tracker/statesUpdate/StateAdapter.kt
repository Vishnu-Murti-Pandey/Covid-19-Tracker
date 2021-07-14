package com.example.covid_19tracker.statesUpdate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19tracker.databinding.ActivityStatesBinding
import com.example.covid_19tracker.databinding.ActivityStatesListItemBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class StateAdapter : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    private var items: ArrayList<StatesData> = ArrayList()

    class StateViewHolder(val binding: ActivityStatesListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = ActivityStatesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StateViewHolder(view)
    }

    fun updateCountry(updatedCountry: ArrayList<StatesData>) {
        items.clear()
        items.addAll(updatedCountry)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val currentItem = items[position]

        holder.binding.stateActive.text = currentItem.active
        holder.binding.stateConfirm.text = currentItem.confirm
        holder.binding.stateDeath.text = currentItem.deaths
        holder.binding.stateRecovered.text = currentItem.recovered
        holder.binding.stateName.text = currentItem.state

    }

    override fun getItemCount(): Int {
        return items.size
    }


}
