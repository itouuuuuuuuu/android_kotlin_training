package com.example.masafumi_ito.myomikujiapp

import android.graphics.Color
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import java.util.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getOmikuji(view: View) {

        val results = arrayOf("大吉", "吉", "凶")

        // 乱数の生成
        val randomGenerator = Random()
        val num = randomGenerator.nextInt(results.size)

        if (num == 0) {
            myTextView.setTextColor(Color.RED)
        } else {
            myTextView.setTextColor(Color.BLACK)
        }

        // 結果の表示
        myTextView.text = results[num]
    }
}

// java
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    public void getOmikuji(View view) {
//        // TextViewの取得
//        TextView tv = (TextView) findViewById(R.id.myTextView);
//        String[] results = { "大吉", "吉", "凶" };
//
//        // 乱数の生成
//        Random randomGenerator = new Random();
//        int num = randomGenerator.nextInt(results.length);
//
//        if (num == 0) {
//            tv.setTextColor(Color.RED);
//        } else {
//            tv.setTextColor(Color.BLACK);
//        }
//
//        // 結果の表示
//        tv.setText(results[num]);
//    }
//}
