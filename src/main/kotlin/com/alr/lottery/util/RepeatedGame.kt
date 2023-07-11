package com.alr.lottery.util

import com.alr.lottery.domain.Result

fun hasAnyGameAlreadyReplayed(results: MutableList<Result>?): Boolean {
    if(results == null) return false
    var startInternalIndex = 1
    for (i in results.indices) {
        for (j in  startInternalIndex until results.size) {
            if(j == results.size) return false
            if (results[i].resultNumbers == results[j].resultNumbers) return true
        }
        startInternalIndex++
    }
    return false
}

fun checkIfGameAlreadReplayed(game: ArrayList<Int>, results: MutableList<Result>?): Boolean {
    results?.forEach {
        if (game == it.resultNumbers) return true
    }
    return false
}