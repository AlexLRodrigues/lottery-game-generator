package com.alr.lottery.util

import com.alr.lottery.domain.Interval
import com.alr.lottery.domain.Result

fun doIntervalAnalisys(intervals: ArrayList<Interval>, result: Result) {
    result.resultNumbers.forEach { number ->
        run outer@ {
            intervals.forEach { interval ->
                if (number == interval.number) {
                    if (interval.lastGameFound == 0) {
                        interval.lastGameFound = result.resultEdition
                    } else {
                        val diff = result.resultEdition - interval.lastGameFound
                        if (interval.maxInterval < diff) interval.maxInterval = diff
                        if (interval.minInterval > diff) interval.minInterval = diff
                        interval.lastGameFound = result.resultEdition
                        interval.totalInterval += diff
                    }
                    interval.ocurrence++
                    return@outer
                }
            }
        }
    }
}

fun getInterval(number: Int, results: MutableList<Result>?): Int {
    if (results == null) return 0
    var interval = 1
    for (i in results.lastIndex downTo 0) {
        if (results[i].resultNumbers.contains(number))
            return interval
        else interval++
    }

    return interval
}