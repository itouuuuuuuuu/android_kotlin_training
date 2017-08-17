package com.example.masafumi_ito.myquizapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    var quizSet = mutableListOf<List<String>>()  // クイズの内容を格納する二次元リスト
    var currentQuiz = 0
    var score = 0

    companion object Factory {
        val EXTRA_MYSCORE = "com.example.masafumi_ito.namescoreapp.EXTRA_MYSCORE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuizSet()  // quiz.txtの読み込み

        setQuiz()      // クイズのセット

        showScore()    // スコアの表示
    }

    fun showScore() {
        scoreText.text = "Score: $score / ${quizSet.size}"
    }

    // アクティビティが再度開始された時（Resultページから戻った時）
    override fun onResume() {
        super.onResume()

        nextButton.text = "Next"

        currentQuiz = 0
        score = 0
        
        setQuiz()
        showScore()
    }

    // quiz.txtの読み込み
    private fun loadQuizSet() {
        BufferedReader(InputStreamReader( assets.open("quiz.txt") )).use {

            // 一行ずつ読み込み
            var line = it.readLine()

            while (line != null) {

                // 一行を分割したリストをリストに入れる
                quizSet.add(line.split(","))

                // 次の行へ
                line = it.readLine()
            }
        }
    }

    // クイズのセット
    private fun setQuiz() {
        qText.text = quizSet[currentQuiz][0]

        // 回答を入れる配列
        var answers = mutableListOf<String>()

        // 回答の選択肢をランダムに
        for(i in 1..3) {
            answers.add(quizSet[currentQuiz][i])
        }
        Collections.shuffle(answers)

        a0Button.text = answers[0]
        a1Button.text = answers[1]
        a2Button.text = answers[2]

        a0Button.isEnabled = true
        a1Button.isEnabled = true
        a2Button.isEnabled = true

        nextButton.isEnabled = false
    }

    // 解答の選択肢がクリックされた時の処理
    fun checkAnswer(view: View) {

        // クリックしたボタンを取得
        val clickedButton = view as Button

        // クリックしたボタンの文字列を取得
        val clickedAnswer = clickedButton.text.toString()

        // 正解の判定
        if(clickedAnswer == quizSet[currentQuiz][1]) {
            clickedButton.text = "○$clickedAnswer"
            score++
        } else {
            clickedButton.text = "x$clickedAnswer"
        }

        // スコアの表示
        showScore()

        // ボタンのenableを設定
        a0Button.isEnabled = false
        a1Button.isEnabled = false
        a2Button.isEnabled = false
        nextButton.isEnabled = true

        // 次のクイズへ進める
        currentQuiz++

        // 最後のクイズの場合Nextボタンの表記を変更
        if(currentQuiz == quizSet.size) {
            nextButton.text = "Check result"
        }
    }

    // 次へボタンを押した時の処理
    fun goNext(view: View) {

        // 最後のクイズの場合ページ移動
        if(currentQuiz == quizSet.size) {

            val intent = Intent(this, MyResult::class.java)                   // インテントの作成
            intent.putExtra(EXTRA_MYSCORE, "Score: $score / ${quizSet.size}")
            startActivity(intent)                                             // インテントを開く
        }
        else {
            setQuiz()
        }

    }


}
