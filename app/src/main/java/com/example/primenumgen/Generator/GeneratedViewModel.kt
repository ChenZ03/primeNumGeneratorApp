package com.example.primenumgen.Generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
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
        val allNum = (startNum..endNum).toList()
        val lock = Mutex()
        if(endNum - startNum > 10000){
            // divide to coroutines
            val numOfCoroutines = floor((endNum - startNum) / 10000.0).toInt()
            coroutineScope {
                async(Dispatchers.Default) {
                    for (i in numOfCoroutines * 1000 until allNum.size) {
                        if (isPrime(allNum[i])) {
                            lock.withLock {
                                primeNumbers.add(allNum[i])
                            }
                        }
                    }
                }
                for (i in 1..numOfCoroutines) {
                    for (n in i * 1000 - 1000..i * 1000) {
                        async(Dispatchers.Default) {
                            if (isPrime(allNum[n])) {
                                lock.withLock {
                                    primeNumbers.add(allNum[n])
                                }
                            }
                        }
                    }
                }
            }

        }else{
            for (i in startNum until endNum) {
                if (isPrime(i)) {
                    primeNumbers.add(i)
                }
            }
        }

        primeNumbers.sort()
        return primeNumbers
    }

}