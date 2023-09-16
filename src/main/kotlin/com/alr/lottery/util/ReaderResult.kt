package com.alr.lottery.util

import com.alr.lottery.LotteryApplication
import com.alr.lottery.domain.Result
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream


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

fun readLotoFacilResultsXlsx(): MutableList<Result>? {

    val classloader = Thread.currentThread().contextClassLoader
    val inputStream = classloader.getResourceAsStream("result-lotofacil.xlsx")
    val workbook = XSSFWorkbook(inputStream)
    val sheet = workbook.getSheetAt(0)
    val results = mutableListOf<Result>()

    sheet.forEachIndexed { index, row ->
        if (index == 0) return@forEachIndexed

        val result = Result(
            resultEdition = row.getCell(0).numericCellValue.toInt(),
            resultDate = row.getCell(1).stringCellValue,
            resultNumbers = arrayListOf(
                row.getCell(2).numericCellValue.toInt(),
                row.getCell(3).numericCellValue.toInt(),
                row.getCell(4).numericCellValue.toInt(),
                row.getCell(5).numericCellValue.toInt(),
                row.getCell(6).numericCellValue.toInt(),
                row.getCell(7).numericCellValue.toInt(),
                row.getCell(8).numericCellValue.toInt(),
                row.getCell(9).numericCellValue.toInt(),
                row.getCell(10).numericCellValue.toInt(),
                row.getCell(11).numericCellValue.toInt(),
                row.getCell(12).numericCellValue.toInt(),
                row.getCell(13).numericCellValue.toInt(),
                row.getCell(14).numericCellValue.toInt(),
                row.getCell(15).numericCellValue.toInt(),
                row.getCell(16).numericCellValue.toInt()
        ),
            winersNumber = row.getCell(17).numericCellValue.toString()
        )
        results.add(result)
    }
    return results
}