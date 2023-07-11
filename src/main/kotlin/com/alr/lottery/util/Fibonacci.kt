package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result
import kotlin.math.sqrt

private fun isPerfectSquare(x: Int): Boolean {
    val s = sqrt(x.toDouble()).toInt()
    return s * s == x
}

private fun isFibonacci(n: Int): Boolean {
    return isPerfectSquare(5 * n * n + 4) ||
            isPerfectSquare(5 * n * n - 4)
}

fun getNumberOfFibonacci(resultNumbers: ArrayList<Int>): Int {
    var numberOfFibonacchi = 0
    resultNumbers.forEach {
        if(isFibonacci(it)) numberOfFibonacchi++
    }
    return numberOfFibonacchi
}

fun doFibonacciAnalisys(fibonacciMaxMinAvg: MaxMinAvg, result: Result) {
    val fibonacciNumbers = getNumberOfFibonacci(result.resultNumbers)
    if (fibonacciMaxMinAvg.max < fibonacciNumbers) fibonacciMaxMinAvg.max = fibonacciNumbers
    if (fibonacciMaxMinAvg.min > fibonacciNumbers) fibonacciMaxMinAvg.min = fibonacciNumbers
    fibonacciMaxMinAvg.total += fibonacciNumbers
}