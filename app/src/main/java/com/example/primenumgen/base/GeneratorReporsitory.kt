package com.example.primenumgen.base

class GeneratorReporsitory {

   fun getPrimeNumbers(startNum : Int , endNum : Int): List<Int> {
       val primeNumbers = mutableListOf<Int>()
       for (i in startNum..endNum) {
           if (isPrime(i)) {
               primeNumbers.add(i)
           }
       }
       return primeNumbers
    }

    private fun isPrime(number: Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }

    companion object{
        val generatedReporsitory = GeneratorReporsitory()
    }
}