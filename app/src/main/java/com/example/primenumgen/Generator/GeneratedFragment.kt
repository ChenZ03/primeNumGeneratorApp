package com.example.primenumgen.Generator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumgen.R
import com.example.primenumgen.databinding.FragmentGeneratedBinding


class GeneratedFragment : Fragment() {
    lateinit var binding: FragmentGeneratedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneratedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        binding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }

}