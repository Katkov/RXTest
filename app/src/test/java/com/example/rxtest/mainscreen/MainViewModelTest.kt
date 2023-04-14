package com.example.rxtest.mainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rxtest.BuildConfig
import com.example.rxtest.helpers.NetworkResult
import com.example.rxtest.networking.model.City
import com.example.rxtest.networking.model.Sports
import com.example.rxtest.repository.FakeRepository
import com.example.rxtest.utils.RxImmediateSchedulerRule
import com.google.common.truth.Truth
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        BuildConfig.IS_TESTING.set(true)
        viewModel = MainViewModel(FakeRepository())
    }

    @Test
    fun getFetchCities() {
        val testObserver = TestObserver<List<City>>()
        viewModel.fetchCities.subscribe(testObserver)
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
    }

    @Test
    fun getFetchSports() {
        val testObserver = TestObserver<Sports>()
        viewModel.fetchSports.subscribe(testObserver)
        testObserver.assertValueCount(2)
        testObserver.assertValues(Sports(), Sports())
        testObserver.assertComplete()
    }

    @Test
    fun getCities() {
        Truth.assertThat(viewModel.result.value).isNotNull()
        Truth.assertThat(viewModel.result.value).isInstanceOf(NetworkResult.Loaded::class.java)
    }

}