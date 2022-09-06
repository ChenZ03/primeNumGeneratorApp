package com.example.primenumgen.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.primenumgen.databinding.ActivityMainBinding
import com.example.primenumgen.Generator.GeneratedActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.generate.setOnClickListener {
            val start = binding.startNumber.text.toString()
            val end = binding.endNumber.text.toString()
            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            } else {
                if (start.toInt() > end.toInt()) {
                    Toast.makeText(this, "Start number must be less than end number", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent(this, GeneratedActivity::class.java)
                    intent.putExtra("start", start.toInt())
                    intent.putExtra("end", end.toInt())
                    startActivity(intent)
                }
            }

        }
    }

}