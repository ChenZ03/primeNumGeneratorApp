// Fragment/ui for generated prime numbers 

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
    // init binding and viewModel
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
        // get values from previous fragment
        val args = arguments
        val startNum = args?.getInt("startNum")
        val endNum = args?.getInt("endNum")

        // set values to header text
        binding.primeNumbers.setText("Prime Numbers (" + startNum + " to " + endNum + "):")

        //launch lifecycleScope to get prime numbers
        lifecycleScope.launch {
            whenStarted {
                // show loading icon
               binding.loading.visibility = View.VISIBLE
            //    get prime numbers from viewModel
                val primeNumbers = withContext(Dispatchers.Default) {
                    viewModel.generatePrimeNums(startNum!!, endNum!!)
                }
                // hide loading icon after getting prime numbers
                binding.loading.visibility = View.GONE
                // set prime numbers to text view
                for (num in primeNumbers) {
                    binding.generatedText.append("$num ,")
                }
                // delete the last comma from text 
                binding.generatedText.setText(binding.generatedText.text.toString().dropLast(1))
            }

        }

        // set onClickListener for back button
        binding.back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }

}