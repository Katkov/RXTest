package com.example.rxtest.networking.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Airline(val id: Int,
                   val name: String,
                   val country: String,
                   val logo: String,
                   val slogan: String,
                   val head_quaters: String,
                   val website: String,
                   val established: String): Parcelable