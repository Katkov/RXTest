package com.example.rxtest.networking.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(var id: Int, var text: String, var isDone: Boolean): Parcelable