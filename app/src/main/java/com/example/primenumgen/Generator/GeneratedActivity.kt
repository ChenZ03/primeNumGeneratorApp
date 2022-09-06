package com.example.primenumgen.Generator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.primenumgen.base.GeneratorReporsitory
import com.example.primenumgen.databinding.ActivityGeneratedBinding

class GeneratedActivity : AppCompatActivity() {
    lateinit var binding : ActivityGeneratedBinding
    val viewModel : GeneratedViewModel by viewModels{
        GeneratedViewModel.Provider(GeneratorReporsitory.generatedReporsitory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratedBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val startNum = intent.getIntExtra("start", 0)
        val endNum = intent.getIntExtra("end", 0)
        binding.back.setOnClickListener {
            finish()
        }
    }
}