package com.cyrilfind.kestion

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Game(val questions: List<GameQuestion>)

data class GameQuestion(
    val text: String,
    val answers: List<GameAnswer>
)

data class GameAnswer(
    val text: String,
    val right: Boolean = false
)
