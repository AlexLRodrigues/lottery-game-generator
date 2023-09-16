package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result

fun doSameNumberPreviousGameAnalisys(maxMinAvg: MaxMinAvg, result: Result, nextResult: Result) {
    var total = 0
    result.resultNumbers.forEach { number ->
        if (nextResult.resultNumbers.contains(number))
            total++
    }
    if (maxMinAvg.max < total) maxMinAvg.max = total
    if (maxMinAvg.min > total) maxMinAvg.min = total
    maxMinAvg.total+=total
}