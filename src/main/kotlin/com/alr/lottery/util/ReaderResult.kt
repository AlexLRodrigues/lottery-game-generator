package com.alr.lottery.util

import com.alr.lottery.LotteryApplication
import com.alr.lottery.domain.Result

fun readMegaResultsHtml(htmlFilePath: String): MutableList<Result>? {

    val fileContent = LotteryApplication::class.java.getResource(htmlFilePath)?.readText()
    val regexTdTable = "<td>(.*?)<table>".toRegex()
    val matchResults = fileContent?.let { regexTdTable.findAll(it) }
    val regexTd = "(?<=<td>)(.*?)(?=</td>)".toRegex()

    val results = mutableListOf<Result>()

    matchResults?.forEach {
        val aux = regexTd.findAll(it.value).toList()
        val result = Result(
            resultEdition = aux[0].value.toInt(),
            resultDate = aux[1].value,
            resultNumbers = arrayListOf(aux[2].value.toInt(), aux[3].value.toInt(), aux[4].value.toInt(), aux[5].value.toInt(), aux[6].value.toInt(), aux[7].value.toInt()),
            winersNumber = aux[8].value
        )

        results.add(result)
    }

    return results
}

fun readLotoFacilResultsHtml(htmlFilePath: String): MutableList<Result>? {

    val fileContent = LotteryApplication::class.java.getResource(htmlFilePath)?.readText()
    val regexTdTable = "<td>(.*?)<table>".toRegex()
    val matchResults = fileContent?.let { regexTdTable.findAll(it) }
    val regexTd = "(?<=<td>)(.*?)(?=</td>)".toRegex()

    val results = mutableListOf<Result>()

    matchResults?.forEach {
        val aux = regexTd.findAll(it.value).toList()
        val result = Result(
            resultEdition = aux[0].value.toInt(),
            resultDate = aux[1].value,
            resultNumbers = arrayListOf(aux[2].value.toInt(), aux[3].value.toInt(), aux[4].value.toInt(), aux[5].value.toInt(), aux[6].value.toInt(), aux[7].value.toInt(), aux[8].value.toInt(), aux[9].value.toInt(), aux[10].value.toInt(), aux[11].value.toInt(), aux[12].value.toInt(), aux[13].value.toInt(), aux[14].value.toInt(), aux[15].value.toInt(), aux[16].value.toInt()),
            winersNumber = aux[18].value
        )

        results.add(result)
    }

    return results
}