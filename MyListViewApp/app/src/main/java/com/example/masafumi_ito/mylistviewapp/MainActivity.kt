package com.example.masafumi_ito.mylistviewapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

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
        val icons = listOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        val names = listOf("Ito", "Soneda", "Yoshimura")
        val locs  = listOf("Kagoshima", "Fukuoka", "Tokyo")

        val users = mutableListOf<User>()
        for(i in 0..names.count()-1){
            users.add(User(icons[i], names[i], locs[i]))
        }

        // Adapter
        val adapter: UserAdapter = UserAdapter(this, users)

        // ListViewに表示
        myListView.adapter = adapter

        // タップした時のイベント
        myListView.setOnItemClickListener { parent, view, pos, id ->

            // タップされたviewからnameを取得
            val name = view.findViewById<View>(R.id.name) as TextView

            // トーストを表示
            Toast.makeText(this, "Tapped: ${name.text}", Toast.LENGTH_SHORT).show()
        }

    }

    // Userの情報を持つデータクラス
    data class User(var icon: Int, var name: String, var loc: String)

    // カスタムadapter
    class UserAdapter(context: Context, users: List<User>) : ArrayAdapter<User>(context, 0, users) {

        // レイアウトファイルからviewを取得するための変数
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // ListViewを表示するための関数をオーバーライド
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var view = convertView

            // リストの項目が読み込まれていない場合
            if (view == null) {

                // list_itemを読み込む
                view = layoutInflater.inflate(R.layout.list_item, parent, false)

            }

            // 何番目のユーザーを取得するか（第3引数から取得してくれる？）
            val user = getItem(position) as User

            (view?.findViewById<View?>(R.id.name) as TextView).text = user.name
            (view?.findViewById<View?>(R.id.loc) as TextView).text = user.loc
            (view?.findViewById<View?>(R.id.icon) as ImageView).setImageBitmap(BitmapFactory.decodeResource(context.resources, user.icon))

            return view!!
        }
    }
}
