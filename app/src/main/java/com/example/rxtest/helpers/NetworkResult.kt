package com.example.rxtest.helpers

sealed class NetworkResult<out T> {
    object Empty : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    class Loaded<out T>(val data: T) : NetworkResult<T>()
    class Error(val message: String) : NetworkResult<Nothing>()
}