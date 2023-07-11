package com.alr.lottery

import com.alr.lottery.domain.GameCategory
import com.alr.lottery.engine.Engine
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LotteryApplication

fun main() {
	val engine = Engine()
//	engine.generateLuckyGames(7, 6, GameCategory.MEGA_SENA)
	engine.generateLuckyGames(1, 16, GameCategory.LOTOFACIL)
}
