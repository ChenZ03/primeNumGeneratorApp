package com.example.primenumgen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.primenumgen.databinding.FragmentHomeBinding
import androidx.navigation.fragment.NavHostFragment


class homeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navigate to next screen
        binding.generate.setOnClickListener {
            val action = homeFragmentDirections.actionHomeFragmentToGeneratedFragment(1,2)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}