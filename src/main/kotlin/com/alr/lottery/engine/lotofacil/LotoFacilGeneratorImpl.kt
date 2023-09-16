package com.alr.lottery.engine.lotofacil

import com.alr.lottery.domain.Interval
import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.engine.Generator
import com.alr.lottery.domain.Result
import com.alr.lottery.domain.SetOfAnalisys
import com.alr.lottery.util.*
import java.math.RoundingMode

class LotoFacilGeneratorImpl : Generator {

    private val FILE_PATH = "/result-lotofacil.html"
    private var numberOfResults = 0
    private val groups = arrayListOf(
        arrayListOf(1,2,3,4,5),
        arrayListOf(6,7,8,9,10),
        arrayListOf(11,12,13,14,15),
        arrayListOf(16,17,18,19,20),
        arrayListOf(21,22,23,24,25)
    )
    private val intervals = initIntervals()

    fun initIntervals(): ArrayList<Interval> {
        val intervals = arrayListOf<Interval>()
        for (i in 1 until 26) intervals.add(Interval(i, 0, 3000, 0, 0, 0, 0))
        return intervals
    }

    override fun generate(numberOfGames: Int, amountOfNumbers: Int) {
//        val results = readLotoFacilResultsHtml(FILE_PATH)
        val results = readLotoFacilResultsXlsx()
        numberOfResults = results?.size ?: 0
        val hasAnyGameAlreadyReplayed = hasAnyGameAlreadyReplayed(results)
        val extractedAnalisys = doAnalisys(results)

        println(extractedAnalisys)

        val luckyResults = arrayListOf<ArrayList<Int>>()
        val lastGame = results?.get(results.size-1)?.resultNumbers

        do {
            val result = randomResult(26, amountOfNumbers)
            if(hasGoodPreviousNumbers(result, lastGame, extractedAnalisys.sameNumberPreviousGameAvg))
                if (hasGoodNumberOffOdds(result, extractedAnalisys.even))
                    if (hasGoodNumberOffPrime(result, extractedAnalisys.prime))
                        if (hasGoodNumberOffFibonacci(result, extractedAnalisys.fibonacci))
                            if (hasGoodNumberOffSameGroup(result, extractedAnalisys.groups))
                                if (hasGoodNumberOffSum(result, extractedAnalisys.sum))
                                    if (hasGoodNumberOffInterval(result, extractedAnalisys.intervals, results))
                                        if (!hasAnyGameAlreadyReplayed) {
                                            if (!checkIfGameAlreadReplayed(ArrayList(result.subList(0,15)), results))
                                                if (!luckyResults.contains(result))
                                                    luckyResults.add(result)
                                        } else {
                                            if (!luckyResults.contains(result))
                                                luckyResults.add(result)
                                        }


        } while (luckyResults.size != numberOfGames)

        println(luckyResults)
    }

    private fun hasGoodPreviousNumbers(result: ArrayList<Int>, lastGame: ArrayList<Int>?, sameNumberPreviousGameAvg: MaxMinAvg): Boolean {
        var totalNumbers = 0
        result.forEach {
            if (lastGame?.contains(it) == true)
                totalNumbers++
        }

        if (totalNumbers > sameNumberPreviousGameAvg.avg + 2
            || totalNumbers < sameNumberPreviousGameAvg.avg -2)
            return false

        return true
    }

    private fun hasGoodNumberOffInterval(result: ArrayList<Int>, intervalsAvg: ArrayList<Interval>, results: MutableList<Result>?): Boolean {
        result.forEach {
            val interval = getInterval(it, results)
            val intervalAvg = intervalsAvg.filter { intervalAvg -> intervalAvg.number == it }
            if (interval !in (intervalAvg[0].avgInterval - 2) .. (intervalAvg[0].avgInterval + 2)){
//                println("interval: $interval avg: ${intervalAvg[0].avgInterval}")
                return false
            }
        }

        return true
    }

    private fun hasGoodNumberOffSum(result: ArrayList<Int>, sumAvg: MaxMinAvg): Boolean {
        val resultWithoutExtraNumbers = result.subList(0,15)
        val sum = getSum(ArrayList(resultWithoutExtraNumbers))
        if (sum in (sumAvg.avg-50)..(sumAvg.avg+40)) return true
        return false
    }

    private fun hasGoodNumberOffSameGroup(result: ArrayList<Int>, groupsAvg: ArrayList<MaxMinAvg>): Boolean {
        var groupIndex = 0
        groups.forEach { _ ->
            val numbersInSameGroup = getAmountNumbersInSameGroup(result, groups[groupIndex])
            if (numbersInSameGroup !in (groupsAvg[groupIndex].avg-2)..(groupsAvg[groupIndex].avg+1)) {
                return false
            }
            groupIndex++
        }
        return true
    }

    private fun hasGoodNumberOffFibonacci(result: ArrayList<Int>, fibonacciAvg: MaxMinAvg): Boolean {
        val number = getNumberOfFibonacci(result)
        if (number in (fibonacciAvg.avg-2)..(fibonacciAvg.avg+2)) return true
        return false
    }

    private fun hasGoodNumberOffPrime(result: ArrayList<Int>, primeAvg: MaxMinAvg): Boolean {
        val primes = getNumberOfPrimes(result)
        if (primes in (primeAvg.avg-2)..(primeAvg.avg+2)) return true
        return false
    }

    private fun hasGoodNumberOffOdds(result: ArrayList<Int>, evenAvg: MaxMinAvg): Boolean {
        val odds = getNumberOfOdds(result)
        if (odds in (evenAvg.avg-2) ..(evenAvg.avg+2)) return true
        return false
    }

    private fun doAnalisys(results: MutableList<Result>?): SetOfAnalisys {

        val evenMaxMinAvg = MaxMinAvg(0, 12, 0, 0)
        val oddMaxMinAvg = MaxMinAvg(0, 13, 0, 0)
        val primeMaxMinAvg = MaxMinAvg(0, 9, 0, 0)
        val fibonacciMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val oneGroupMaxMinAvg = MaxMinAvg(0, 15, 0, 0)
        val secondGroupMaxMinAvg = MaxMinAvg(0, 15, 0, 0)
        val thirdGroupMaxMinAvg = MaxMinAvg(0, 15, 0, 0)
        val fourthGroupMaxMinAvg = MaxMinAvg(0, 15, 0, 0)
        val fifthGroupMaxMinAvg = MaxMinAvg(0, 15, 0, 0)
        val sumMaxMinAvg = MaxMinAvg(0, 270, 0, 0)
        val sameNumberPreviousGameAvg = MaxMinAvg(0, 16, 0, 0)

        results?.forEachIndexed { index, it ->
            doEvenAnalisys(evenMaxMinAvg, it)
            doOddAnalisys(oddMaxMinAvg, it)
            doPrimeAnalisys(primeMaxMinAvg, it)
            doFibonacciAnalisys(fibonacciMaxMinAvg, it)
            doGroupAnalisys(oneGroupMaxMinAvg, it, groups[0])
            doGroupAnalisys(secondGroupMaxMinAvg, it, groups[1])
            doGroupAnalisys(thirdGroupMaxMinAvg, it, groups[2])
            doGroupAnalisys(fourthGroupMaxMinAvg, it, groups[3])
            doGroupAnalisys(fifthGroupMaxMinAvg, it, groups[4])
            doSumAnalisys(sumMaxMinAvg, it)
            doIntervalAnalisys(intervals, it)
            if (index < results.size-1)
                doSameNumberPreviousGameAnalisys(sameNumberPreviousGameAvg, results[index], results[index+1])
        }

        setAvgValues(evenMaxMinAvg,
            oddMaxMinAvg,
            primeMaxMinAvg,
            fibonacciMaxMinAvg,
            oneGroupMaxMinAvg,
            secondGroupMaxMinAvg,
            thirdGroupMaxMinAvg,
            fourthGroupMaxMinAvg,
            fifthGroupMaxMinAvg,
            sumMaxMinAvg,
            sameNumberPreviousGameAvg
        )

        setAvgInterval()

        return SetOfAnalisys(
            evenMaxMinAvg,
            oddMaxMinAvg,
            primeMaxMinAvg,
            fibonacciMaxMinAvg,
            arrayListOf(
                oneGroupMaxMinAvg,
                secondGroupMaxMinAvg,
                thirdGroupMaxMinAvg,
                fourthGroupMaxMinAvg,
                fifthGroupMaxMinAvg
            ),
            sumMaxMinAvg,
            intervals,
            sameNumberPreviousGameAvg
        )
    }

    private fun setAvgInterval() {

        intervals.forEach {
            if (it.ocurrence == 0) it.avgInterval = 0
            else it.avgInterval = (it.totalInterval.toDouble()/it.ocurrence.toDouble()).toBigDecimal().setScale(0, RoundingMode.HALF_UP).toInt()
        }
    }

    private fun setAvgValues(vararg maxMinArgs: MaxMinAvg) {
        maxMinArgs.forEach {
            it.avg = (it.total.toDouble() / numberOfResults.toDouble()).toBigDecimal().setScale(0, RoundingMode.HALF_UP).toInt()
        }
    }
}