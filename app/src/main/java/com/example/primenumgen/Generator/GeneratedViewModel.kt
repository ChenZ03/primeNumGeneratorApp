package com.example.primenumgen.Generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.primenumgen.base.BaseViewModel
import com.example.primenumgen.base.GeneratorReporsitory

class GeneratedViewModel(private val repository: GeneratorReporsitory) : BaseViewModel() {

    fun getPrimeNumbers() {
        repository.getPrimeNumbers(startNum.value!!, endNum.value!!)
    }

    class Provider(private val repository: GeneratorReporsitory): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(GeneratedViewModel::class.java)) {
                return GeneratedViewModel(repository) as T
            }

            throw IllegalArgumentException("ViewModel is not valid")
        }
    }

}