package com.cyrilfind.kestion

import android.graphics.drawable.Drawable
import com.cyrilfind.kestion.jsonadapter.DrawableResName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Game(val questions: List<GameQuestion>)

data class GameQuestion(
    val text: String,
    @field:Json(name = "image_res_name")
    @field:DrawableResName
    val image: Drawable?,
    val answers: List<GameAnswer>
)

data class GameAnswer(
    val text: String,
    val right: Boolean = false
)
