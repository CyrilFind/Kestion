package com.cyrilfind.kestion

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.cyrilfind.kestion.extensions.flash
import com.cyrilfind.kestion.jsonadapter.DrawableResNameAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var answerButtons: List<Button>
    private var count: Int by Delegates.observable(-1) { _, _, newValue ->
        count_text_view.text = "Question: ${newValue + 1}"
    }
    private var score: Int by Delegates.observable(-1) { _, _, newValue ->
        score_text_view.text = "Score: $newValue"
    }
    private val currentQuestion: GameQuestion
        get() = game.questions[count]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        answerButtons = listOf(button_1, button_2, button_3, button_4)
        answerButtons.forEach { view -> view.setOnClickListener { onClickAnswer(view) } }
        count = 0
        score = 0
        initGame()
        initQuestion()
    }

    private fun initGame() {
        val json = resources.openRawResource(R.raw.game).bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder().add(DrawableResNameAdapter(this)).build()
        game = GameJsonAdapter(moshi).fromJson(json) ?: error("json load error")
    }

    private fun initQuestion() {
        question_text_view.text = currentQuestion.text
        currentQuestion.image?.let {
            question_image_view.visibility = View.VISIBLE
            question_image_view.setImageDrawable(it)
        } ?: run {
            question_image_view.visibility = View.GONE
        }
        answerButtons.forEachIndexed { index, button -> button.text = currentQuestion.answers[index].text }
    }

    private fun onClickAnswer(button: Button) {
        val index = answerButtons.indexOf(button)
        val answer = currentQuestion.answers[index]
        if (answer.right) win(button) else fail(button)
    }

    private fun win(button: Button) {
        button.flash(android.R.color.holo_green_light) {
            score++
            if (game.questions.count() - 1 == count) {
                showDialog()
            } else {
                count++
                initQuestion()
            }
        }
    }

    private fun fail(button: Button) {
        button.flash(android.R.color.holo_red_light) {
            if (score > 0) score--
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setMessage("Bravo !")
            .setCancelable(false)
            .setPositiveButton("Recommencer") { _, _ -> recreate() }
            .create()
            .show()
    }
}
