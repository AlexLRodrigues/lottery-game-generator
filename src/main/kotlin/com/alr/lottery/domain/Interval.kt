package com.alr.lottery.domain

data class Interval(
    val number: Int,
    var maxInterval: Int,
    var minInterval: Int,
    var totalInterval: Int,
    var ocurrence: Int,
    var avgInterval: Int,
    var lastGameFound: Int
)
