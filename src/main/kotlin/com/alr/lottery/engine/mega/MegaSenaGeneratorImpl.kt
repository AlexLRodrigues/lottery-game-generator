package com.alr.lottery.engine.mega

import com.alr.lottery.domain.Interval
import com.alr.lottery.domain.MaxMinAvg
import com.alr.lottery.engine.Generator
import com.alr.lottery.domain.Result
import com.alr.lottery.domain.SetOfAnalisys
import com.alr.lottery.util.*
import java.math.RoundingMode

class MegaSenaGeneratorImpl : Generator {

    private val FILE_PATH = "/result-mega.html"
    private var numberOfResults = 0

    private val groups = arrayListOf(
        arrayListOf(1,2,3,4,5,6,7,8,9,10),
        arrayListOf(11,12,13,14,15,16,17,18,19,20),
        arrayListOf(21,22,23,24,25,26,27,28,29,30),
        arrayListOf(31,32,33,34,35,36,37,38,39,40),
        arrayListOf(41,42,43,44,45,46,47,48,49,50),
        arrayListOf(51,52,53,54,55,56,57,58,59,60)
    )

    private val intervals = initIntervals()

    fun initIntervals(): ArrayList<Interval> {
        val intervals = arrayListOf<Interval>()
        for (i in 1 until 61) intervals.add(Interval(i, 0, 3000, 0, 0, 0, 0))
        return intervals
    }

    override fun generate(numberOfGames: Int, amountOfNumbers: Int) {
        val results = readMegaResultsHtml(FILE_PATH)
        numberOfResults = results?.size ?: 0
        val hasAnyGameAlreadyReplayed = hasAnyGameAlreadyReplayed(results)
        val extractedAnalisys = doAnalisys(results)

        println(extractedAnalisys)

        val luckyResults = arrayListOf<ArrayList<Int>>()

        do {
            val result = randomResult(61, amountOfNumbers)
            if (hasGoodNumberOffOdds(result, extractedAnalisys.even))
                if (hasGoodNumberOffPrime(result, extractedAnalisys.prime))
                    if (hasGoodNumberOffFibonacci(result, extractedAnalisys.fibonacci))
                        if (hasGoodNumberOffSameGroup(result, extractedAnalisys.groups))
                            if (hasGoodNumberOffSum(result, extractedAnalisys.sum))
                                if (hasGoodNumberOffInterval(result, extractedAnalisys.intervals, results))
                                    if (!hasAnyGameAlreadyReplayed) {
                                        if (!checkIfGameAlreadReplayed(result, results))
                                            if (!luckyResults.contains(result))
                                                luckyResults.add(result)
                                    } else {
                                        if (!luckyResults.contains(result))
                                            luckyResults.add(result)
                                    }


        } while (luckyResults.size != numberOfGames)

        println(luckyResults)
    }

    private fun hasGoodNumberOffInterval(result: ArrayList<Int>, intervalsAvg: ArrayList<Interval>, results: MutableList<Result>?): Boolean {
        result.forEach {
            val interval = getInterval(it, results)
            val intervalAvg = intervalsAvg.filter { intervalAvg -> intervalAvg.number == it }
            if (interval !in (intervalAvg[0].avgInterval - 9) .. (intervalAvg[0].avgInterval + 15)){
//                println("interval: $interval avg: ${intervalAvg[0].avgInterval}")
                return false
            }
        }

        return true
    }

    private fun hasGoodNumberOffSum(result: ArrayList<Int>, sumAvg: MaxMinAvg): Boolean {
        val sum = getSum(result)
        if (sum in (sumAvg.avg-70)..(sumAvg.avg+70)) return true
        return false
    }

    private fun hasGoodNumberOffSameGroup(result: ArrayList<Int>, groupsAvg: ArrayList<MaxMinAvg>): Boolean {
        var groupIndex = 0
        groups.forEach { _ ->
            val numbersInSameGroup = getAmountNumbersInSameGroup(result, groups[groupIndex])
            if (numbersInSameGroup !in (groupsAvg[groupIndex].avg-1)..(groupsAvg[groupIndex].avg+1)) {
                return false
            }
            groupIndex++
        }
        return true
    }

    private fun hasGoodNumberOffFibonacci(result: ArrayList<Int>, fibonacciAvg: MaxMinAvg): Boolean {
        val number = getNumberOfFibonacci(result)
        if (number in (fibonacciAvg.avg-1)..(fibonacciAvg.avg+1)) return true
        return false
    }

    private fun hasGoodNumberOffPrime(result: ArrayList<Int>, primeAvg: MaxMinAvg): Boolean {
        val primes = getNumberOfPrimes(result)
        if (primes in (primeAvg.avg-1)..(primeAvg.avg+1)) return true
        return false
    }

    private fun hasGoodNumberOffOdds(result: ArrayList<Int>, evenAvg: MaxMinAvg): Boolean {
        val odds = getNumberOfOdds(result)
        if (odds in (evenAvg.avg-1) ..(evenAvg.avg+1)) return true
        return false
    }

    private fun doAnalisys(results: MutableList<Result>?): SetOfAnalisys {

        val evenMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val oddMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val primeMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val fibonacciMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val oneGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val secondGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val thirdGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val fourthGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val fifthGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val sixthGroupMaxMinAvg = MaxMinAvg(0, 6, 0, 0)
        val sumMaxMinAvg = MaxMinAvg(0, 400, 0, 0)

        results?.forEach {
            doEvenAnalisys(evenMaxMinAvg, it)
            doOddAnalisys(oddMaxMinAvg, it)
            doPrimeAnalisys(primeMaxMinAvg, it)
            doFibonacciAnalisys(fibonacciMaxMinAvg, it)
            doGroupAnalisys(oneGroupMaxMinAvg, it, groups[0])
            doGroupAnalisys(secondGroupMaxMinAvg, it, groups[1])
            doGroupAnalisys(thirdGroupMaxMinAvg, it, groups[2])
            doGroupAnalisys(fourthGroupMaxMinAvg, it, groups[3])
            doGroupAnalisys(fifthGroupMaxMinAvg, it, groups[4])
            doGroupAnalisys(sixthGroupMaxMinAvg, it, groups[5])
            doSumAnalisys(sumMaxMinAvg, it)
            doIntervalAnalisys(intervals, it)
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
            sixthGroupMaxMinAvg,
            sumMaxMinAvg
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
                fifthGroupMaxMinAvg,
                sixthGroupMaxMinAvg
            ),
            sumMaxMinAvg,
            intervals
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