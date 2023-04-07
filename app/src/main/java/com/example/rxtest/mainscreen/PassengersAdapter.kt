package com.example.rxtest.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rxtest.databinding.ItemPersonBinding
import com.example.rxtest.networking.model.Person
import com.example.rxtest.networking.model.src

class PassengersAdapter(private val navigate: (Person) -> Unit) :
    PagingDataAdapter<Person, PassengersAdapter.ViewHolder>(
        DiffCallback()
    ) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class ViewHolder(
        private val binding: ItemPersonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.apply {
                itemPersonName.text = person.name
                itemPersonTrips.text = person.trips.toString()
                person.src(itemPersonImage)
                binding.root.setOnClickListener {
                    navigate.invoke(person)
                }
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}