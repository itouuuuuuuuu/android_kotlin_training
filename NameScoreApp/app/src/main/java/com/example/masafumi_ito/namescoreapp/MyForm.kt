package com.example.masafumi_ito.namescoreapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_my_form.*

class MyForm : AppCompatActivity() {

    companion object Factory {
        val EXTRA_MYNAME = "com.example.masafumi_ito.namescoreapp.EXTRA_MYNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_form)
    }

    fun getScore(view: View) {

        // EditTextの中身を取得
        val myName = myEditText.text.trim().toString()

        if(myName.isBlank()){

            // エラー処理1
            myEditText.error = "Please enter your name!"

            // エラー処理2（トースト）
//            Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()

            // エラー処理3（ダイアログ）
//            AlertDialog.Builder(this)
//                    .setTitle("Error")
//                    .setMessage("Please enter your name!")
//                    .setPositiveButton("OK", null)
//                    .show()

        } else {

            // 次のページへ
            val intent = Intent(this, MyResult::class.java) // 次のページのインテントを作成
            intent.putExtra(EXTRA_MYNAME, myName)           // 次のページへデータを渡す
            startActivity(intent)                           // インテントを開く

        }
    }
}
