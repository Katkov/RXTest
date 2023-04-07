package com.example.rxtest.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rxtest.R
import com.example.rxtest.databinding.FragmentSecondBinding
import com.example.rxtest.networking.model.src
import dagger.android.support.DaggerFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : DaggerFragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val person = args.person
        binding.apply {
            userId.text = person._id
            userName.text = person.name
            userTrips.text = "Trips: ${person.trips}"
            person.src(userImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}