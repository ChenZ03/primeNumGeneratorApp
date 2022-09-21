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
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumgen.R
import com.example.primenumgen.databinding.FragmentGeneratedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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


        lifecycleScope.launch {
            whenStarted {
               binding.loading.visibility = View.VISIBLE
                val primeNumbers = withContext(Dispatchers.Default) {
                    viewModel.generatePrimeNums(startNum!!, endNum!!)
                }
                binding.loading.visibility = View.GONE
                if (primeNumbers.size == 0){
                    binding.empty.visibility = View.VISIBLE
                }
                for (num in primeNumbers) {
                    binding.generatedText.append("$num ,")
                }
                binding.generatedText.setText(binding.generatedText.text.toString().dropLast(1))
            }

        }

        Log.i("GeneratedFragment", "args: $args")
        binding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }

}