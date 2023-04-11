package com.example.rxtest.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rxtest.R
import com.example.rxtest.databinding.FragmentFirstBinding
import com.example.rxtest.helpers.hideKeyboard
import com.example.rxtest.networking.model.Todo
import dagger.android.support.DaggerFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideKeyboard()
        binding.todoList.adapter = adapter
        binding.todoFab.setOnClickListener {
            navigate(null)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.addToComposable(viewModel.result.subscribe { showItems(it) })
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearComposable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigate(todo: Todo?) {
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(todo)
        findNavController().navigate(action)
    }

    private fun showItems(result: Pair<Style, List<Todo>>) {
        when (result.first) {
            Style.SHOW_ALL -> adapter.submitList(result.second)
            Style.SHOW_DONE -> adapter.submitList(result.second.filter { it.isDone })
            Style.SHOW_UNDONE -> adapter.submitList(result.second.filter { !it.isDone })
        }
        showListOrDefaultText(result)
    }

    private fun showListOrDefaultText(result: Pair<Style, List<Todo>>) {
        binding.apply {
            todoList.visibility =
                if (result.second.isEmpty()) View.GONE else View.VISIBLE
            todoDefaultText.visibility =
                if (result.second.isEmpty()) View.VISIBLE else View.GONE
        }
    }

}