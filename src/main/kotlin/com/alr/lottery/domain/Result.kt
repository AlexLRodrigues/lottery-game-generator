package com.alr.lottery.domain

data class Result(
    val resultEdition: Int,
    val resultDate: String,
    val resultNumbers: ArrayList<Int>,
    val winersNumber: String
)
