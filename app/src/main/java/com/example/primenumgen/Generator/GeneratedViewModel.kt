package com.example.primenumgen.Generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.floor


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
        val lock = Mutex()
        if(endNum - startNum > 10000){
            // divide to coroutines scope and join with dispatchers
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            val jobs = mutableListOf<Job>()
            val step = floor((endNum - startNum) / 10000.0).toInt()
            for (i in startNum until endNum + 1 step step) {
                jobs.add(coroutineScope.launch {
                    for (j in i until i + step) {
                        if (isPrime(j)) {
                            lock.withLock {
                                primeNumbers.add(j)
                            }
                        }
                    }
                })
            }
            jobs.forEach { it.join() }
            primeNumbers.sort()

            return primeNumbers
        }else{
            for (i in startNum until endNum + 1) {
                if (isPrime(i)) {
                    primeNumbers.add(i)
                }
            }

            primeNumbers.sort()

            return primeNumbers
        }
    }

}