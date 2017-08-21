package com.example.masafumi_ito.myscheduler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.masafumi_ito.myscheduler.model.Schedule
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_schedule_edit.*

class ScheduleEditActivity : AppCompatActivity() {

    // realm操作用のプロパティ（kotlinのプロパティは初期値が必要だが、lateinitで初期値を後から設定できる）
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)

        realm = Realm.getDefaultInstance()
    }

    // 保存ボタンがタップされた時の処理
    fun onSaveTapped(view: View) {

        realm.executeTransaction {

//            // 保存されているスケジュールのidの最大値を取得
//            val maxId: Long = realm.where(Schedule::class.java).max("id").toLong()
//
//            val schedule = realm.createObject(Schedule::class.java, 0)

//            schedule.title = titleTextView.text.toString()

//            schedule.title = "test"
//            schedule.date = null
//            schedule.detail = "test"

        }

        Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show()

        finish()

    }

    override fun onDestroy() {
        super.onDestroy()

        // realmインスタンスを破棄
        realm.close()
    }
}
