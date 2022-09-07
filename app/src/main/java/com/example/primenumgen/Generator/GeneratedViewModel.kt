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
        val allNum = (startNum..endNum).toList()
        val lock = Mutex()
        if(endNum - startNum > 10000){
            // divide to coroutines
            val numOfCoroutines = floor((endNum - startNum) / 10000.0).toInt()
            coroutineScope {
                launch(Dispatchers.Default) {
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
                        launch(Dispatchers.Default) {
                            if (isPrime(allNum[n])) {
                                lock.withLock {
                                    primeNumbers.add(allNum[n])
                                }
                            }
                        }
                    }
                }
                viewModelScope.launch {
                    _loading.emit(false)
                }
            }

        }else{
            for (i in startNum until endNum + 1) {
                if (isPrime(i)) {
                    primeNumbers.add(i)
                }
            }
            viewModelScope.launch {
                _loading.emit(false)
            }
        }

        primeNumbers.sort()

        return primeNumbers
    }

}