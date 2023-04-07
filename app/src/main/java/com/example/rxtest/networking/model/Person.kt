package com.example.rxtest.networking.model

import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(val _id: String = "",
                  val name: String = "",
                  val trips: Int = 0,
                  val airline: List<Airline> = listOf(),
                  val __v: Int = 0
) : Parcelable

fun Person.src(imageView: ImageView) {
    if (this.airline.isNotEmpty()) {
        val glide = Glide.with(imageView.context)
            .load(this.airline[0].logo)
            .transition(DrawableTransitionOptions.withCrossFade())
        glide.into(imageView)
    }
}