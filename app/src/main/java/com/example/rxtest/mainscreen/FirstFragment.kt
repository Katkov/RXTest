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
import com.example.rxtest.networking.model.Todo
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

    private var adapter: TodoAdapter = TodoAdapter { todo -> navigate(todo) }

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
        binding.todoList.adapter = adapter
        viewModel.result.subscribe {
            adapter.submitList(it)
        }
        binding.todoFab.setOnClickListener {
            navigate(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigate(todo: Todo?) {
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(todo)
        findNavController().navigate(action)
    }

}