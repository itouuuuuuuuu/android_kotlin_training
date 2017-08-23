package com.example.masafumi_ito.myscheduler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.masafumi_ito.myscheduler.model.Schedule
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : AppCompatActivity() {

    // realm操作用のプロパティ（kotlinのプロパティは初期値が必要だが、lateinitで初期値を後から設定できる）
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)

        realm = Realm.getDefaultInstance()

        // インテントからidを取得（受け取れなかった場合は-1を取得）
        val scheduleId: Long = intent.getLongExtra("schedule_id", -1)

        // 更新の場合
        if(scheduleId != (-1).toLong()) {

            // DBからidに一致するデータを取得
            val results = realm.where(Schedule::class.java).equalTo("id", scheduleId).findAll()
            val schedule = results.first() as Schedule

            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.format(schedule.date)

            // 項目にDBの内容を反映
            titleEditText.setText(schedule.title)
            dateEditText.setText(date)
            detailEditText.setText(schedule.detail)

        }
    }

    // 保存ボタンがタップされた時の処理
    fun onSaveTapped(view: View) {

        val sdf = SimpleDateFormat("yyyy/MM/dd")
        var dateParse = Date()

        try {
            dateParse = sdf.parse(dateEditText.text.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val date = dateParse

        // インテントからidを取得（受け取れなかった場合は-1を取得）
        val scheduleId: Long = intent.getLongExtra("schedule_id", -1)

        // 更新の場合
        if(scheduleId != (-1).toLong()) {

            // DBからidに一致するデータを取得
            val results = realm.where(Schedule::class.java).equalTo("id", scheduleId).findAll()

            realm.executeTransaction {

                // スケジュールオブジェクトを取得
                val schedule = results.first() as Schedule

                // レイアウトファイルから入力された値を取得
                schedule.title = titleEditText.text.toString()
                schedule.date = date
                schedule.detail = detailEditText.text.toString()
            }

            // トーストの表示
            Toast.makeText(this, "更新しました", Toast.LENGTH_SHORT).show()

        } else {

            realm.executeTransaction {

                // 保存されているスケジュールのidの最大値を取得
                val maxId = if (realm.where(Schedule::class.java).max("id") != null)
                    realm.where(Schedule::class.java).max("id")
                else
                    -1

                // idに１を足して新しいオブジェクトを作成
                val schedule = realm.createObject(Schedule::class.java, maxId.toLong() + 1)

                // レイアウトファイルから入力された値を取得
                schedule.title = titleEditText.text.toString()
                schedule.date = date
                schedule.detail = detailEditText.text.toString()
            }

            // トーストの表示
            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show()

        }

        // インテントを終了
        finish()
    }

    fun onDeleteTapped(view: View) {

        // インテントからidを取得（受け取れなかった場合は-1を取得）
        val scheduleId: Long = intent.getLongExtra("schedule_id", -1)

        // 更新の場合
        if(scheduleId != (-1).toLong()) {

            // DBからidに一致するデータを取得
            val results = realm.where(Schedule::class.java).equalTo("id", scheduleId).findAll()

            realm.executeTransaction {

                // スケジュールオブジェクトを取得
                val schedule = results.first() as Schedule

                // オブジェクトを削除
                schedule.deleteFromRealm()
            }

            // トーストの表示
            Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show()
        }

        // インテントを終了
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        // realmインスタンスを破棄
        realm.close()
    }
}
