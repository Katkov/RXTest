package com.example.rxtest.mainscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.rxtest.R
import com.example.rxtest.databinding.FragmentFirstBinding
import com.example.rxtest.helpers.NetworkResult
import com.example.rxtest.networking.model.City
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : DaggerFragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.citiesState.collect { uiState ->
                    // New value received
                    when (uiState) {
                        is NetworkResult.Empty -> showNoResults()
                        is NetworkResult.Loading -> showProgress()
                        is NetworkResult.Error -> showError()
                        is NetworkResult.Loaded -> showCities(uiState.data)
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showNoResults() {

    }

    fun showProgress() {

    }

    fun showError() {

    }

    fun showCities(cities: List<City>) {
        binding.citiesList.adapter = CitiesAdapter(cities) {
            Log.d("FirstFragment", "Go to details ${it.name}")
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(it.name)
            findNavController().navigate(action)
        }
    }
}