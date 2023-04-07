package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import com.example.rxtest.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val fetchCities: Observable<List<City>>
        get() {
            return repository
                .fetchCities("London")
                .observeOn(AndroidSchedulers.mainThread())
        }

    val fetchSports: Observable<Sports>
        get() {
            return repository
                .fetchCities("London")
                .flatMap { Observable.fromIterable(it) }
                .flatMap { city -> repository.fetchSports(city.name) }
                .observeOn(AndroidSchedulers.mainThread())
        }

    fun addToComposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearComposable() {
        compositeDisposable.clear()
    }

}