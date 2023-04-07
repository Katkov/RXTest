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
import com.example.rxtest.networking.model.Sports
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.operators.completable.CompletableMergeDelayErrorIterable
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

    private lateinit var adapter: CitiesAdapter

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

    }

    override fun onStart() {
        super.onStart()
        val fetchCitiesDisposable = viewModel
            .fetchCities
            .doOnSubscribe{ showProgress() }
            .subscribe (
                { cities -> showCities(cities) },
                { _ -> showError()}
            )
        viewModel.addToComposable(fetchCitiesDisposable)
//        val fetchSportsDisposable = viewModel
//            .fetchSports
//            .doOnSubscribe { showProgress() }
//            .subscribe(
//                { sports -> showSports(sports) },
//                { _ -> showError()}
//            )
//        viewModel.addToComposable(fetchSportsDisposable)
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearComposable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showSports(sports: Sports) {
        Log.d("FirstFragment", "Show Sports")
    }

    fun showNoResults() {

    }

    fun showProgress() {
        Log.d("FirstFragment", "Show Progress")
    }

    fun showError() {
        Log.d("FirstFragment", "Show Error")
    }

    fun showCities(cities: List<City>) {
        Log.d("FirstFragment", "Show Cities")
        adapter = CitiesAdapter {
            //adapter.remove(city = city)
            Log.d("FirstFragment", "Go to details ${it.name}")
            //val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(it.name)
            //findNavController().navigate(action)
            deleteItem(it)
        }
        binding.citiesList.adapter = adapter
        adapter.submitList(cities)
    }

    fun deleteItem (city: City) {
        val list = adapter.currentList
        val newList = mutableListOf<City>()
        newList.addAll(list)
        newList.remove(city)
        adapter.submitList(newList)
    }
}