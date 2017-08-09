package com.example.masafumi_ito.mylistviewapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*  // kotlin_android_extensionsでxmlのidを読み込み

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // データを準備
//        // val items = mutableListOf<String>() // リストの準備
//        // for (i in 1..30)                    // リストの内容を作成
//        // {
//        //     items.add("item-$i")
//        // }
//        // Kotlinでは上の処理をこんな感じでも書ける
//        val items = (1..30).toMutableList().map { num -> "items-$num" }
//
//        // Adapter
//        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.list_item, items)
//
//        // ListViewに表示
//        myListView.emptyView = emptyView  // Listが空の場合
//        myListView.adapter = adapter

        // データを準備
        val users = mutableListOf<User>()
        val icons = listOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        val names = listOf("Ito", "Soneda", "Yoshimura")
        val locs = listOf("Kagoshima", "Fukuoka", "Tokyo")

//        for(i in 1..3){
//            val user: User = User()
//        }

    }

    data class User
    (
            var icon: Bitmap,
            var name: String,
            var loc: String
    )
}
