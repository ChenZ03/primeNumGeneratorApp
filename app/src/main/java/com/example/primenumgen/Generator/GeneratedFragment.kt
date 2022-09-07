package com.example.primenumgen.Generator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
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
        val args = arguments
        val startNum = args?.getInt("startNum")
        val endNum = args?.getInt("endNum")
        binding.primeNumbers.setText("Prime Numbers (" + startNum + " to " + endNum + "):")

        viewModel.isLoading.asLiveData().observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it) View.VISIBLE else View.GONE
        }

        lifecycleScope.launch {
            val primeNumbers : MutableList<Int> = viewModel.generatePrimeNums(startNum!!, endNum!!)
            if (primeNumbers.isEmpty()) {
                binding.empty.visibility = View.VISIBLE
            } else {
                for (num in primeNumbers) {
                    binding.generatedText.append("$num ,  ")
                }
                binding.generatedText.setText(binding.generatedText.text.toString().dropLast(3))
            }
        }

        Log.i("GeneratedFragment", "args: $args")
        binding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }

}