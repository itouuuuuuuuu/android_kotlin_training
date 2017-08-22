package com.example.masafumi_ito.myscheduler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.masafumi_ito.myscheduler.model.Schedule
import io.realm.Realm
import io.realm.RealmBaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import io.realm.RealmResults
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.*
import android.widget.TextView
import android.widget.ListAdapter
import android.widget.Toast
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    // realm操作用のプロパティ（kotlinのプロパティは初期値が必要だが、lateinitで初期値を後から設定できる）
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // realmクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()

        // 保存したスケジュール情報を全て取得
        val schedules = realm.where(Schedule::class.java).findAll()

        // 取得したスケジュール情報をアダプターにセット
        listView.adapter = ScheduleAdapter(schedules)

        // タップした時のイベント
        listView.setOnItemClickListener { parent, view, position, id ->

            // スケジュールのインスタンスを取得
            val schedule = parent.getItemAtPosition(position) as Schedule

            val intent = Intent(this, ScheduleEditActivity::class.java)       // インテントの作成
            intent.putExtra("schedule_id", schedule.id)                       // スケジュールのidを渡す
            startActivity(intent)                                             // インテントを開く
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // realmインスタンスを破棄
        realm.close()
    }

    // FABがタップされた時の処理
    fun onClickFab(view: View) {

        // スケジュール登録ページへ移動
        val intent = Intent(this, ScheduleEditActivity::class.java)
        startActivity(intent)
    }

    // アダプター（realmを使用するアダプターはRealmBaseAdapterを継承する）
    class ScheduleAdapter(data: OrderedRealmCollection<Schedule>?) : RealmBaseAdapter<Schedule>(data) {

        // リストのひとつの項目を保存するクラス
        private class ViewHolder(var date : TextView? = null, var title: TextView? = null)

        // リストの項目が表示される度に実行される処理
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var view = convertView          // リストのひとつの項目のビュー
            var viewHolder = ViewHolder()   // リストのひとつの項目を保存するクラスの初期化

            // リストの項目が読み込まれていない場合（初回表示）
            if (view == null) {

                // list_itemを読み込む
                view = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_2, parent, false)

                // view（リストのひとつの項目）にタグ付けするためのインスタンスを作成（表示する度にfindViewByIdするのは効率が悪いため）
                viewHolder = ViewHolder(
                        view.findViewById(android.R.id.text1), // ひとつの項目内のTextView
                        view.findViewById(android.R.id.text2)  // ひとつの項目内のTextView
                )

                // タグ付け(リストのひとつの項目にviewHolderを保存)
                view.tag = viewHolder

            }
            // リストが読み込まれている場合
            else {
                // タグからviewHolderを作成（リストのひとつの項目に保存されているviewHolderを読み込み）
                viewHolder = view.tag as ViewHolder
            }

            // リストのviewに内容を登録していく処理
            val schedule = getItem(position) as Schedule
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val formatDate = sdf.format(schedule.date)

            viewHolder.date?.text = formatDate
            viewHolder.title?.text = schedule.title

            return view!!
        }
    }
}
