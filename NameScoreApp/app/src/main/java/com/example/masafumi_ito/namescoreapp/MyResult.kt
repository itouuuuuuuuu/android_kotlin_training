package com.example.masafumi_ito.namescoreapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_result.*
import java.util.*

class MyResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_result)

        // インテントからデータを受け取る
        val myName = intent.getStringExtra(MyForm.EXTRA_MYNAME)

        nameLabel.text = "${myName}の点数は…"

        val randomGenerator = Random()
        val score = randomGenerator.nextInt(101)

        scoreLabel.text = "${score}点!!"
    }
}
