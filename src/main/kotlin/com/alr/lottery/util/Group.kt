package com.alr.lottery.util

import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.domain.Result

fun doGroupAnalisys(groupMaxMinAvg: MaxMinAvg, result: Result, group: ArrayList<Int>) {
    var total = 0
    result.resultNumbers.forEach {
        if (group.contains(it)) total++
    }
    if (groupMaxMinAvg.max < total) groupMaxMinAvg.max = total
    if (groupMaxMinAvg.min > total) groupMaxMinAvg.min = total
    groupMaxMinAvg.total += total
}

fun getAmountNumbersInSameGroup(result: ArrayList<Int>, group: ArrayList<Int>): Int {
    var total = 0

    result.forEach {
        if (group.contains(it)) total ++
    }

    return total
}