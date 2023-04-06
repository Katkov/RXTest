package com.example.rxtest.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxtest.databinding.ItemListBinding
import com.example.rxtest.networking.model.City

class CitiesAdapter(private val cities: List<City>,
                    private val navigate: (City) -> Unit) : RecyclerView.Adapter<CitiesAdapter.CitiesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesHolder {
        val itemBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CitiesHolder, position: Int) {
        val city: City = cities[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = cities.size

    inner class CitiesHolder(private val itemBinding: ItemListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(city: City) {
            itemBinding.apply {
                itemCity.text = city.name
                itemCountry.text = city.country
                root.setOnClickListener { navigate.invoke(city) }
            }
        }
    }
}