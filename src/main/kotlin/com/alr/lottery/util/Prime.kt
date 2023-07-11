package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result

private fun isPrime(number: Int): Boolean {
    if (number == 1) return false
    for (check in 2 until number) {
        if (number % check == 0) {
            return false
        }
    }
    return true
}

fun getNumberOfPrimes(resultNumbers: ArrayList<Int>): Int {
    var numberOfPrimes = 0
    resultNumbers.forEach {
        if(isPrime(it)) numberOfPrimes++
    }
    return numberOfPrimes
}

fun doPrimeAnalisys(primeMaxMinAvg: MaxMinAvg, result: Result) {
    val primeNumbers = getNumberOfPrimes(result.resultNumbers)
    if (primeMaxMinAvg.max < primeNumbers) primeMaxMinAvg.max = primeNumbers
    if (primeMaxMinAvg.min > primeNumbers) primeMaxMinAvg.min = primeNumbers
    primeMaxMinAvg.total += primeNumbers
}