package com.alr.lottery.engine

interface Generator {
    fun generate(numberOfGames: Int, amountOfNumbers: Int)
}