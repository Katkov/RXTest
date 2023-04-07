package com.example.rxtest.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rxtest.databinding.ItemListBinding
import com.example.rxtest.networking.model.City

class CitiesAdapter(private val navigate: (City) -> Unit) :
    ListAdapter<City, CitiesAdapter.CitiesHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesHolder {
        return CitiesHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CitiesHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    inner class CitiesHolder(private val itemBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(city: City) {
            itemBinding.apply {
                itemCity.text = city.name
                itemCountry.text = city.country
                root.setOnClickListener { navigate.invoke(city) }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
}