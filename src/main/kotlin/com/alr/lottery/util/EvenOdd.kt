package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result

fun getNumberOfOdds(list: MutableList<Int>?): Int {
    var numberOfOdds = 0
    list?.forEach {
        if (it % 2 != 0) {
            numberOfOdds++
        }
    }
    return numberOfOdds
}

fun doEvenAnalisys(evenMaxMinAvg: MaxMinAvg, result: Result) {
    val evenNumbers = result.resultNumbers.size - getNumberOfOdds(result.resultNumbers)
    if (evenMaxMinAvg.max < evenNumbers) evenMaxMinAvg.max = evenNumbers
    if (evenMaxMinAvg.min > evenNumbers) evenMaxMinAvg.min = evenNumbers
    evenMaxMinAvg.total += evenNumbers
}

fun doOddAnalisys(oddMaxMinAvg: MaxMinAvg, result: Result) {
    val oddNumbers = getNumberOfOdds(result.resultNumbers)
    if (oddMaxMinAvg.max < oddNumbers) oddMaxMinAvg.max = oddNumbers
    if (oddMaxMinAvg.min > oddNumbers) oddMaxMinAvg.min = oddNumbers
    oddMaxMinAvg.total += oddNumbers
}