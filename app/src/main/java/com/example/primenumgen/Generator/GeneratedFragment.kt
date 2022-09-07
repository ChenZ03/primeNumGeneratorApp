package com.example.primenumgen.Generator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumgen.R
import com.example.primenumgen.databinding.FragmentGeneratedBinding
import kotlinx.coroutines.launch


class GeneratedFragment : Fragment() {
    lateinit var binding: FragmentGeneratedBinding
    private val viewModel: GeneratedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneratedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val initialString = "Prime Numbers : "
        val args = arguments
        val startNum = args?.getInt("startNum")
        val endNum = args?.getInt("endNum")
        lifecycleScope.launch {
            val primNumbers : MutableList<Int> = viewModel.generatePrimeNums(startNum!!, endNum!!)
            binding.generatedText.text = initialString
            for (i in primNumbers) {
                binding.generatedText.append("$i, ")
            }
        }

        Log.i("GeneratedFragment", "args: $args")
        binding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }

}