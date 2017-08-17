package com.example.masafumi_ito.myquizapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_my_result.*

class MyResult:AppCompatActivity() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_result)

        // インテントからデータを受け取り、表示する
        resultText.text = intent.getStringExtra(MainActivity.EXTRA_MYSCORE)

    }

    fun goBack(view: View) {
        finish() // アクティビティの終了
    }
}
