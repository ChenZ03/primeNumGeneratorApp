package com.example.primenumgen.Generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class GeneratedViewModel() : ViewModel() {

    private fun isPrime(number: Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }

    suspend fun generatePrimeNums(startNum: Int, endNum: Int): MutableList<Int> {
        val primeNumbers = mutableListOf<Int>()
        for (i in startNum..endNum) {
            if (isPrime(i)) {
                primeNumbers.add(i)
            }
        }
        return primeNumbers
    }

}