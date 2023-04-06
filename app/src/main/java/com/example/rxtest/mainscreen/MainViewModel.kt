package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import com.example.rxtest.networking.model.City
import com.example.rxtest.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val fetchCities: Single<List<City>>
        get() {
            return repository.fetchCities("London").cache()
                .observeOn(AndroidSchedulers.mainThread())
        }

    fun addToComposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearComposable() {
        compositeDisposable.clear()
    }

}