// fragment for home page 

package com.example.primenumgen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.primenumgen.databinding.FragmentHomeBinding
import androidx.navigation.fragment.NavHostFragment


class homeFragment : Fragment() {
    // init binding 
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
        // set onClick for generate button 
        binding.generate.setOnClickListener {
            val startNum = binding.startNumber.text.toString()
            val endNum = binding.endNumber.text.toString()
            // check if start and end number is empty
            if(startNum.isEmpty() || endNum.isEmpty()) {
                Toast.makeText(context, "Please enter a number in all fields", Toast.LENGTH_SHORT).show()
            }else{
                // Check if start number is greater than end number
                if (startNum.toInt() > endNum.toInt()) {
                    Toast.makeText(context, "Start number must be less than end number", Toast.LENGTH_SHORT).show()
                }else{
                    // navigate to result fragment with passing args
                    val action = homeFragmentDirections.actionHomeFragmentToGeneratedFragment(startNum.toInt(), endNum.toInt())
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }
        }
    }
}