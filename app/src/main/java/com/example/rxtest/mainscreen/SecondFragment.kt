package com.example.rxtest.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rxtest.R
import com.example.rxtest.databinding.FragmentSecondBinding
import com.example.rxtest.networking.model.Todo
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : DaggerFragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: SecondFragmentArgs by navArgs()

    private var todo: Todo? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todo = args.todo
        todo?.let {
            binding.secondTodoEditText.setText(it.text)
            binding.secondTodoCheck.isChecked = it.isDone
            binding.secondAddTodoBtn.text = "Edit In Todo List"
        }
        setAddTodoBtnClick()
        if (todo == null) binding.secondRemoveTodoBtn.visibility = View.GONE
        setRemoveBtnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAddTodoBtnClick() {
        binding.secondAddTodoBtn.setOnClickListener {
            if (binding.secondTodoEditText.text.isNotEmpty()) {
                if (todo == null) todo = Todo(-1, "", false)
                todo!!.text = binding.secondTodoEditText.text.toString()
                todo!!.isDone = binding.secondTodoCheck.isChecked
                viewModel.addOrEdit(todo!!)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }

    private fun setRemoveBtnClick() {
        binding.secondRemoveTodoBtn.setOnClickListener {
            viewModel.delete(todo!!)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}