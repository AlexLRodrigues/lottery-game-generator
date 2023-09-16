package com.alr.lottery.domain

data class SetOfAnalisys(
        var even: MaxMinAvg,
        var odd: MaxMinAvg,
        var prime: MaxMinAvg,
        var fibonacci: MaxMinAvg,
        var groups: ArrayList<MaxMinAvg>,
        var sum: MaxMinAvg,
        var intervals: ArrayList<Interval>,
        var sameNumberPreviousGameAvg: MaxMinAvg
)