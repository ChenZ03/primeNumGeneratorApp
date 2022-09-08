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
    protected val _loading : MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isLoading : SharedFlow<Boolean> = _loading

    private fun isPrime(number: Int): Boolean {
        for (i in 2 until number) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }

    suspend fun generatePrimeNums(startNum: Int, endNum: Int): MutableList<Int> {
        viewModelScope.launch {
            _loading.emit(true)
        }
        val primeNumbers = mutableListOf<Int>()
        val lock = Mutex()
        if(endNum - startNum > 10000){
            // divide to coroutines scope async and await all job done
            val jobs = mutableListOf<Job>()
            val numPerJob = floor((endNum - startNum) / 10000.0).toInt()
            for (i in 0..10000){
                val start = startNum + i * numPerJob
                val end = if(i == 10000) endNum else startNum + (i + 1) * numPerJob
                jobs.add(viewModelScope.launch {
                    for (num in start..end){
                        if(isPrime(num)){
                            lock.withLock {
                                primeNumbers.add(num)
                            }
                        }
                    }
                })
            }
            jobs.joinAll()
            primeNumbers.sort()
            viewModelScope.launch {
                _loading.emit(false)
            }
            return primeNumbers
        }else{
            for (i in startNum until endNum + 1) {
                if (isPrime(i)) {
                    primeNumbers.add(i)
                }
            }
            viewModelScope.launch {
                _loading.emit(false)
            }
            primeNumbers.sort()

            return primeNumbers
        }
    }

}