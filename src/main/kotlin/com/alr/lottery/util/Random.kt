package com.alr.lottery.util

import java.util.*
import kotlin.collections.ArrayList

val random = Random()

private fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from // from(incluso) e to(excluso)
}

fun randomResult(max: Int, amountOfNumbers: Int): ArrayList<Int> {
    val generatedResult = arrayListOf<Int>()
    do {
        val number = rand(1, max)
        if (!generatedResult.contains(number))
            generatedResult.add(number)
    } while (generatedResult.size < amountOfNumbers)

    generatedResult.sort()

    return generatedResult
}