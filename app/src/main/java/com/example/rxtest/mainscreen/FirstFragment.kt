package com.example.rxtest.mainscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.rxtest.R
import com.example.rxtest.databinding.FragmentFirstBinding
import com.example.rxtest.networking.model.Person
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : DaggerFragment(R.layout.fragment_first) {

    private var hasInitiatedInitialCall = false

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private var job: Job? = null

    private val adapter = PassengersAdapter { person -> navigate(person) }

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

        setAdapter()

        //prevents the method being called again onbackpressed pressed.
        if (!hasInitiatedInitialCall) {
            getPassengers()
            hasInitiatedInitialCall = true
        }
    }

    private fun getPassengers() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getPassengers().collect {
                adapter.submitData(it)
            }
        }
    }

    private fun setAdapter() {
        binding.passengersList.adapter = adapter.withLoadStateFooter(
            LoadingStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener {

            if (it.refresh is LoadState.Error) {
                Toast.makeText(
                    requireContext(),
                    "There was a problem fetching data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate(person: Person) {
        findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(person))
    }
}