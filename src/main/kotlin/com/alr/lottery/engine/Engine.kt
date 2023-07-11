package com.alr.lottery.engine

import com.alr.lottery.domain.GameCategory
import com.alr.lottery.engine.lotofacil.LotoFacilGeneratorImpl
import com.alr.lottery.engine.mega.MegaSenaGeneratorImpl

class Engine {

    fun generateLuckyGames(numberOfGames: Int, amountOfNumbers: Int, gameCategory: GameCategory){
        val generator: Generator = getGeneratorByCategory(gameCategory)
        generator.generate(numberOfGames, amountOfNumbers)
    }

    private fun getGeneratorByCategory(gameCategory: GameCategory): Generator {
        val generator: Generator = when (gameCategory) {
            GameCategory.MEGA_SENA -> MegaSenaGeneratorImpl()
            GameCategory.LOTOFACIL -> LotoFacilGeneratorImpl()
        }
        return generator
    }
}