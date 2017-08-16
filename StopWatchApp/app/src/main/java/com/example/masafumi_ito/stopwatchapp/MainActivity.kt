package com.example.masafumi_ito.stopwatchapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var startTime: Long = 0
    var elapsedTime: Long = 0

    val handler: Handler = Handler()
    var updateTimer: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // startボタンのみ押せる
        setButtonState(true, false, false)

    }

    // ストップウォッチのボタン設定（押せるか押せないか）
    fun setButtonState(start: Boolean, stop: Boolean, reset: Boolean) {
        startButton.isEnabled = start
        stopButton.isEnabled  = stop
        resetButton.isEnabled = reset
    }

    // スタートボタンを押した時の処理
    fun startTimer(view: View) {

        // 起動してからの経過時間（ミリ秒）
        startTime = SystemClock.elapsedRealtime()

        // 一定時間ごとに現在の経過時間を表示
        updateTimer = object : Runnable {  // 無名クラスの作成
            override fun run() {

                // スタートボタンを押してからの経過時間を取得
                val t = SystemClock.elapsedRealtime() - startTime + elapsedTime

                // 経過時間を表示するフォーマットを指定
                val sdf = SimpleDateFormat("mm:ss.SS", Locale.US)

                // 経過時間を表示
                timerLabel.text = sdf.format(t)

                // ハンドラーを定期的に実行
                handler.postDelayed(this, 10)
            }
        }

        // ハンドラーの開始
        handler.postDelayed(updateTimer, 10)

        // ストップ・リセットボタンのみを押せるように設定
        setButtonState(false, true, true)
    }

    // ストップボタンを押した時の処理
    fun stopTimer(view: View) {

        // 経過時間を保存
        elapsedTime += SystemClock.elapsedRealtime() - startTime

        // ハンドラーを停止
        handler.removeCallbacks(updateTimer)

        // スタート・リセットボタンのみを押せるように設定
        setButtonState(true, false, true)
    }

    // リセットボタンを押した時の処理
    fun resetTimer(view: View) {

        // ハンドラーを停止
        handler.removeCallbacks(updateTimer)

        elapsedTime = 0
        timerLabel.text = "00:00.00"

        // スタートボタンのみを押せるように設定
        setButtonState(true, false, false)
    }
}
