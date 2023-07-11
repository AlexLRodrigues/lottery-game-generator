package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result

fun doSumAnalisys(sumMaxMinAvg: MaxMinAvg, result: Result) {
    val sumResult = result.resultNumbers.sum()
    if (sumMaxMinAvg.max < sumResult) sumMaxMinAvg.max = sumResult
    if (sumMaxMinAvg.min > sumResult) sumMaxMinAvg.min = sumResult
    sumMaxMinAvg.total += sumResult
}

fun getSum(result: ArrayList<Int>): Int {
    return result.sum()
}
